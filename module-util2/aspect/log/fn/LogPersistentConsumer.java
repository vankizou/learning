package com.zmn.biz.amislc.aspect.log.fn;

import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistentRegister;

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
     * @param logRegister   接口注册信息
     * @param logPersistent 日志动作注解
     * @param inputParamMap 输入参数
     * @param outputResult  输出响应
     * @author zoufanqi
     * @since 2023/6/7 10:44
     */
    void consumePersistentLog(LogPersistentRegister logRegister, LogPersistent logPersistent, Map<String, Object> inputParamMap, Object outputResult);

}
