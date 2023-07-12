package com.zoufanqi.aspect.log;

import com.zmn.biz.amislc.BizAmislcException;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.fn.LogPersistentConsumer;
import com.zmn.biz.amislc.aspect.log.handler.AbstractPersistentLogHandler;
import com.zmn.biz.amislc.model.entity.AmislcOperateLog;
import com.zmn.biz.amislc.services.interfaces.IAmislcOperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;


/**
 * 日志切面，主要处理逻辑：
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
public class LogAspect extends BaseLogAspect {
    @Autowired
    private IAmislcOperateLogService operateLogService;
    /**
     * 持久化日志处理逻辑
     */
    private final LogPersistentConsumer persistentLogConsumer = new LogPersistentConsumer() {
        @Override
        public void consumePersistentLog(LogEnvTypeEnum envType, Class<?> targetClazz, LogPersistent bosLogPersistent, Map<String, Object> inputParamMap, Object outputParam) {
            final AmislcOperateLog log = AbstractPersistentLogHandler.buildPersistentLog(envType, targetClazz, bosLogPersistent, inputParamMap, outputParam);
            if (Objects.nonNull(log)) {
                operateLogService.save(log);
            }
        }
    };

    protected LogAspect(LogEnvTypeEnum envType) {
        super(envType);
    }

    public Object doAround(ProceedingJoinPoint point) {
        return doAround(point, null);
    }

    public Object doAround(ProceedingJoinPoint point, String operator) {
        return this.doProcess(point, persistentLogConsumer, operator);
    }

    @Override
    protected boolean isParamException(Throwable throwable) {
        return throwable instanceof BizAmislcException || throwable instanceof IllegalArgumentException;
    }
}
