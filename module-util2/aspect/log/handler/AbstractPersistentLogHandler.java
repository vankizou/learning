package com.zoufanqi.aspect.log.handler;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogObjTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogOperateTypeEnum;
import com.zmn.biz.amislc.model.entity.AmislcOperateLog;
import com.zmn.common.dto2.AMISResponseDTO;
import com.zmn.common.dto2.ResponseDTO;
import com.zmn.common.utils.collection.CollectionUtil;

import java.time.LocalDateTime;
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
public abstract class AbstractPersistentLogHandler {
    public static AmislcOperateLog buildPersistentLog(
            LogEnvTypeEnum envType,
            Class<?> targetClazz,
            LogPersistent logPersistent,
            Map<String, Object> inputParamMap,
            Object outputParam
    ) {
        final String clsName = targetClazz.getSimpleName();
        LogObjTypeEnum matchHandler = null;

        for (LogObjTypeEnum handler : LogObjTypeEnum.values()) {
            if (handler.getDubboClsMatches().apply(clsName.toUpperCase())) {
                // 根据类名判断是否为该类型
                matchHandler = handler;
                break;
            }
        }
        if (Objects.isNull(matchHandler)) {
            return null;
        }

        final boolean resultSuccess = isResultSuccess(outputParam);
        final boolean ignore = (resultSuccess && !logPersistent.persistentSuccess()) ||
                (!resultSuccess && !logPersistent.persistentFail());
        if (ignore) {
            return null;
        }

        // 组织日志数据
        final AmislcOperateLog log = new AmislcOperateLog();
        log.setEnvType(envType.getEnvType());
        log.setObjType(matchHandler.getType());
        log.setObjTypeName(matchHandler.getName());
        log.setOperateType(logPersistent.value().getType());
        log.setOperateParam(buildOperateParam(inputParamMap));

        // 填充appId（如果有）
        log.setAppId(NumberUtil.parseInt(Optional.ofNullable(inputParamMap.get("appId")).orElse("0").toString(), 0));
        // 无直接传递appId参数
        if (Objects.isNull(log.getAppId()) || log.getAppId() <= 0) {
            for (Object o : inputParamMap.values()) {
                final String str = JSON.toJSONString(o);
                if (!JSONUtil.isTypeJSONObject(str)) {
                    continue;
                }
                // 从参数值中获取appId
                final Integer appId = JSON.parseObject(str).getInteger("appId");
                if (Objects.isNull(appId)) {
                    continue;
                }
                log.setAppId(appId);
                break;
            }
        }

        log.setOperateResult(JSON.toJSONString(outputParam));
        log.setOperateStatus(resultSuccess ? 1 : 2);
        log.setCreateTime(LocalDateTime.now());

        final String objIdParamName = matchHandler.getObjIdParamName();
        final String operatorParamName = "operator";

        // 组装objId和creator
        consumeParam(
                inputParamMap,
                map -> {
                    // 获取objId
                    boolean objIdOk = false;
                    if (Objects.isNull(log.getObjId())) {
                        final Object objIdTmp = map.get(objIdParamName);
                        if (Objects.nonNull(objIdTmp) && NumberUtil.isNumber(objIdTmp.toString())) {
                            log.setObjId(NumberUtil.parseInt(objIdTmp.toString()));
                            objIdOk = true;
                        }
                    } else {
                        objIdOk = true;
                    }

                    // 获取operator
                    boolean operatorOk = false;
                    if (StrUtil.isBlank(log.getCreater())) {
                        final Object operatorTmp = map.get(operatorParamName);
                        if (Objects.nonNull(operatorTmp)) {
                            log.setCreater(operatorTmp.toString());
                            operatorOk = true;
                        }
                    } else {
                        operatorOk = true;
                    }
                    return objIdOk && operatorOk;
                }
        );

        // 同一个方法承担添加和修改的双重动作处理
        if (logPersistent.value() == LogOperateTypeEnum.INSERT_OR_UPDATE) {
            log.setOperateType(
                    (Objects.nonNull(log.getObjId()) && log.getObjId() > 0 ?
                            LogOperateTypeEnum.UPDATE : LogOperateTypeEnum.INSERT).getType()
            );
        }

        return log;
    }

    private static String buildOperateParam(Map<String, Object> inputParamMap) {
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

    private static boolean isResultSuccess(Object result) {
        if (result instanceof AMISResponseDTO) {
            return Objects.equals(AMISResponseDTO.SUCCESS, ((AMISResponseDTO<?>) result).getStatus());
        } else if (result instanceof ResponseDTO) {
            return ((ResponseDTO<?>) result).isSuccess();
        }
        return false;
    }

    private static void consumeParam(Map<String, Object> inputParamMap, Function<Map<String, ?>, Boolean> paramConsumer) {
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

}
