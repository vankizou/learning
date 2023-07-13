package com.zmn.biz.amislc.aspect.log.builder.common;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistentRegister;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogObjTypeEnum;
import com.zmn.common.utils.collection.CollectionUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * 业务日志持久化处理基类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 13:55
 **/
public abstract class BasePersistentLogBuilder<T> {
    /**
     * map参数转入库字符串
     *
     * @param inputParamMap 入参
     * @return 入库参数
     * @author zoufanqi
     * @since 2023/7/13 09:20
     */
    protected static String convertMapParamToString(Map<String, Object> inputParamMap) {
        if (CollectionUtil.isNullOrEmpty(inputParamMap)) {
            return "{}";
        }
        if (inputParamMap.size() == 1) {
            final Optional<Object> first = inputParamMap.values().stream().findFirst();
            final String firstStr;
            if (JSONUtil.isTypeJSONObject(firstStr = JSON.toJSONString(first))) {
                return firstStr;
            }
        }
        return JSON.toJSONString(inputParamMap);
    }

    /**
     * 遍历每个入参
     *
     * @param inputParamMap 入参
     * @param paramConsumer 消费者
     * @author zoufanqi
     * @since 2023/7/13 09:18
     */
    protected static void consumeParam(Map<String, Object> inputParamMap, Function<Map<String, ?>, Boolean> paramConsumer) {
        // 参数列表
        Boolean interrupt = paramConsumer.apply(inputParamMap);
        if (interrupt) {
            return;
        }

        // 参数值为包装类型
        for (Object param : inputParamMap.values()) {
            final String strParam = JSON.toJSONString(param);
            if (!JSONUtil.isTypeJSONObject(strParam)) {
                continue;
            }
            final JSONObject pm = JSON.parseObject(strParam);
            interrupt = paramConsumer.apply(pm);
            if (interrupt) {
                break;
            }
        }
    }

    public T build(
            LogEnvTypeEnum envType, LogPersistentRegister logRegister, LogPersistent logPersistent,
            Map<String, Object> inputParamMap, Object outputResult
    ) {
        // 模块对象
        final LogObjTypeEnum matchedObjType = Objects.isNull(logRegister) ? null : logRegister.value();
        if (Objects.isNull(matchedObjType)) {
            return null;
        }

        // 结果
        final boolean resultSuccess = isResultSuccess(outputResult);
        final boolean ignore = (resultSuccess && !logPersistent.persistentSuccess()) ||
                (!resultSuccess && !logPersistent.persistentFail());
        if (ignore) {
            return null;
        }

        // 构建entity
        return this.buildPersistentLog(envType, matchedObjType, logPersistent, inputParamMap, outputResult, resultSuccess);
    }

    /**
     * 结果是否成功
     *
     * @param result 结果
     * @return t/f
     * @author zoufanqi
     * @since 2023/7/13 09:08
     */
    protected abstract boolean isResultSuccess(Object result);

    /**
     * 构建entity
     *
     * @param envType         环境
     * @param objType         模块对象
     * @param logPersistent   日志注解
     * @param inputParamMap   入参
     * @param outputResult    结果
     * @param isResultSuccess 结果是否成功
     * @return entity
     * @author zoufanqi
     * @since 2023/7/13 09:15
     */
    protected abstract T buildPersistentLog(
            LogEnvTypeEnum envType,
            LogObjTypeEnum objType,
            LogPersistent logPersistent,
            Map<String, Object> inputParamMap,
            Object outputResult,
            boolean isResultSuccess
    );
}
