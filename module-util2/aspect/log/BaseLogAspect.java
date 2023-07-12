package com.zmn.biz.amislc.aspect.log;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zmn.biz.amislc.aspect.log.anno.*;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogOperateTypeEnum;
import com.zmn.biz.amislc.aspect.log.fn.LogPersistentConsumer;
import com.zmn.biz.amislc.aspect.log.handler.LogPersistentImpl;
import com.zmn.biz.amislc.common.dio.BaseAddDIO;
import com.zmn.biz.amislc.common.dio.BaseModifyDIO;
import com.zmn.biz.amislc.common.dio.BaseQueryDIO;
import com.zmn.common.constant.StatusConsts;
import com.zmn.common.dto2.AMISResponseDTO;
import com.zmn.common.dto2.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author liumohui
 * @version v1.0
 * @since 2022/6/10 9:44
 **/
@Slf4j
public abstract class BaseLogAspect {
    private final static LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 环境类型；1admin，2dubbo
     */
    private final LogEnvTypeEnum envType;

    private final LogPersistent ADD_LP = new LogPersistentImpl(LogOperateTypeEnum.INSERT);

    private final LogPersistent MODIFY_LP = new LogPersistentImpl(LogOperateTypeEnum.UPDATE);

    private final LogPersistent DELETE_LP = new LogPersistentImpl(LogOperateTypeEnum.DELETE);


    protected BaseLogAspect(LogEnvTypeEnum envType) {
        this.envType = envType;
    }

    public Object doProcess(ProceedingJoinPoint point, LogPersistentConsumer persistentLogConsumer) {
        return doProcess(point, persistentLogConsumer, null);
    }

    public Object doProcess(ProceedingJoinPoint point, LogPersistentConsumer persistentLogConsumer, String operator) {
        // 切入点信息
        final Class<?> targetClazz = point.getTarget().getClass();
        final Signature signature = point.getSignature();

        // 持久化日志注册信息
        final LogRegister logRegister = targetClazz.getAnnotation(LogRegister.class);

        final String methodName = signature.getName();
        final String clsMethodName = targetClazz.getSimpleName() + "#" + signature.getName();

        // 方法类型
        final Method targetMethod = ((MethodSignature) signature).getMethod();
        final Class<?> returnClassType = targetMethod.getReturnType();

        final LogPersistent persistentLog = targetMethod.getAnnotation(LogPersistent.class);

        Map<String, Object> inputParamMap = null;
        Object outputResponse = null;
        try {
            // 获取参数上的日志输出设置
            final LogPrintIgnore logIgnore = targetMethod.getAnnotation(LogPrintIgnore.class);
            final LogPrintIgnoreInputParam logIgnoreInput = targetMethod.getAnnotation(LogPrintIgnoreInputParam.class);
            final LogPrintIgnoreOutputResponse logIgnoreOutput = targetMethod.getAnnotation(LogPrintIgnoreOutputResponse.class);

            final boolean ignoreInput = Objects.isNull(persistentLog) && (Objects.nonNull(logIgnore) || Objects.nonNull(logIgnoreInput));
            final boolean ignoreOutput = Objects.isNull(persistentLog) && (Objects.nonNull(logIgnore) || Objects.nonNull(logIgnoreOutput));

            // 入参
            final Object[] objects = point.getArgs();
            inputParamMap = this.buildParamMap(targetMethod, objects, operator);
            log.info("调用方法{}, 入参: {}", clsMethodName, ignoreInput ? "***" : JSON.toJSONString(inputParamMap));

            outputResponse = point.proceed();

            // 响应
            log.info("调用方法{}, 响应: {}", clsMethodName, ignoreOutput ? "***" : JSON.toJSONString(outputResponse));
            return outputResponse;
        } catch (Throwable ex) {
            log.info("调用方法{}, 异常", clsMethodName, ex);
            // 异常信息转换
            if (this.isParamException(ex) && returnClassType == ResponseDTO.class) {
                outputResponse = envType == LogEnvTypeEnum.ADMIN ?
                        AMISResponseDTO.fail(StatusConsts.ERROR_PARAMS, ex.getMessage()) :
                        ResponseDTO.fail(StatusConsts.ERROR_PARAMS, ex.getMessage());
            } else {
                outputResponse = envType == LogEnvTypeEnum.ADMIN ? AMISResponseDTO.fail(ex.getMessage()) : ResponseDTO.fail(ex.getMessage());
            }
            return outputResponse;
        } finally {
            // 持久化日志
            outIf:
            if (Objects.nonNull(persistentLogConsumer)) {
                // 接口有持久化注解
                if (Objects.nonNull(persistentLog)) {
                    persistentLogConsumer.consumePersistentLog(envType, logRegister, persistentLog, inputParamMap, outputResponse);
                    break outIf;
                }
                // 没有持久化注解，但方法名以[add]开头，自动记入添加
                boolean matches = methodName.startsWith("add");
                if (matches) {
                    persistentLogConsumer.consumePersistentLog(envType, logRegister, ADD_LP, inputParamMap, outputResponse);
                    break outIf;
                }
                // 没有持久化注解，但方法名以[modify]开头，自动记入修改
                matches = methodName.startsWith("modify");
                if (matches) {
                    persistentLogConsumer.consumePersistentLog(envType, logRegister, MODIFY_LP, inputParamMap, outputResponse);
                    break outIf;
                }
                // 没有持久化注解，但方法名以[delete]开头，自动记入删除
                matches = methodName.startsWith("delete");
                if (matches) {
                    persistentLogConsumer.consumePersistentLog(envType, logRegister, DELETE_LP, inputParamMap, outputResponse);
                }
            }
        }
    }

    /**
     * 是否为自定义异常
     *
     * @param throwable 异常
     * @return t/f
     * @author liumohui
     * @since 2022/11/09 10:42
     */
    protected abstract boolean isParamException(Throwable throwable);

    /**
     * 拼接参数
     *
     * @param method 方法
     * @param args   参数
     * @return 参数
     * @author zoufanqi
     * @since 2023/6/7 15:14
     */
    private Map<String, Object> buildParamMap(Method method, Object[] args, String operator) {
        if (Objects.isNull(args)) {
            return Collections.emptyMap();
        }
        // 获取方法的参数名
        final String[] params = DISCOVERER.getParameterNames(method);
        if (Objects.isNull(params)) {
            return Collections.emptyMap();
        }

        // 将参数名与参数值对应起来
        final Map<String, Object> paramMap = new LinkedHashMap<>(8);
        for (int i = 0, len = params.length; i < len; i++) {
            final Object arg = args[i];
            if (arg instanceof BaseAddDIO) {
                // 自动填充操作人
                if (StrUtil.isNotBlank(operator)) {
                    ((BaseAddDIO) arg).setOperator(operator);
                }
                ((BaseAddDIO) arg).validate();
            } else if (arg instanceof BaseModifyDIO) {
                // 自动填充操作人
                if (StrUtil.isNotBlank(operator)) {
                    ((BaseModifyDIO) arg).setOperator(operator);
                }
                ((BaseModifyDIO) arg).validate();
            } else if (arg instanceof BaseQueryDIO) {
                ((BaseQueryDIO) arg).validate();
            }
            paramMap.put(params[i], args[i]);
        }
        return paramMap;
    }
}