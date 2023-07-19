package com.zmn.biz.amislc.aspect.log;

import com.zmn.biz.amislc.BizAmislcException;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistentRegister;
import com.zmn.biz.amislc.aspect.log.builder.AmislcPersistentLogBuilder;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.fn.LogPersistentConsumer;
import com.zmn.biz.amislc.model.entity.AmislcOperateLog;
import com.zmn.biz.amislc.services.interfaces.log.IAmislcOperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;


/**
 * 日志切面业务处理，主要处理逻辑：
 * 1. 统一对所有DIO字段做校验
 * 2. 统一打印所有Admin/Dubbo接口的出入信息
 * 3. 统一对接口[增删改]操作进行日志持久化（默认处理以[add/modify/delete]开头的方法，也可以添加[@LogPersistent]注解自定义指定）
 * 4. 统一处理异常逻辑，Admin返回AMISResponseDTO，Dubbo返回ResponseDTO
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/4 16:24
 **/
@Slf4j
public abstract class BaseLogAspectService extends BaseLogAspect {
    private final LogEnvTypeEnum envType;

    @Autowired
    private IAmislcOperateLogService operateLogService;

    /**
     * 持久化日志处理逻辑
     */
    private final LogPersistentConsumer persistentLogConsumer = new LogPersistentConsumer() {
        private final AmislcPersistentLogBuilder logBuilder = new AmislcPersistentLogBuilder();

        @Override
        public void consumePersistentLog(
                LogPersistentRegister logRegister,
                LogPersistent bosLogPersistent,
                Map<String, Object> inputParamMap,
                Object outputResult
        ) {
            final AmislcOperateLog log = logBuilder.build(envType, logRegister, bosLogPersistent, inputParamMap, outputResult);
            if (Objects.nonNull(log)) {
                operateLogService.save(log);
            }
        }
    };

    protected BaseLogAspectService(LogEnvTypeEnum envType) {
        this.envType = envType;
    }

    public Object doAround(ProceedingJoinPoint point) {
        return this.doProcess(point, persistentLogConsumer);
    }

    @Override
    protected boolean isBizException(Throwable ex) {
        return ex instanceof BizAmislcException || ex instanceof IllegalArgumentException;
    }

    /**
     * AOP入口
     *
     * @param point 切点
     * @return 响应
     * @author zoufanqi
     * @since 2023/7/13 10:11
     */
    public abstract Object around(ProceedingJoinPoint point);

}
