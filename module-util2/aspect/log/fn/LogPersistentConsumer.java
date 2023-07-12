package com.zmn.biz.amislc.aspect.log.fn;

import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.anno.LogRegister;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;

import java.util.Map;

/**
 * 持久化日志消费者
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 10:43
 **/
@FunctionalInterface
public interface LogPersistentConsumer {

    /**
     * 持久化日志消费
     *
     * @param envType       环境类型
     * @param logRegister   接口注册信息
     * @param logPersistent 日志动作注解
     * @param inputParamMap 输入参数
     * @param outputParam   输出响应
     * @author zoufanqi
     * @since 2023/6/7 10:44
     */
    void consumePersistentLog(LogEnvTypeEnum envType, LogRegister logRegister, LogPersistent logPersistent, Map<String, Object> inputParamMap, Object outputParam);

}
