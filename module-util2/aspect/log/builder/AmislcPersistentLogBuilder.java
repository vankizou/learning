package com.zmn.biz.amislc.aspect.log.builder;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.enums.LogEnvTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogObjTypeEnum;
import com.zmn.biz.amislc.aspect.log.enums.LogOperateTypeEnum;
import com.zmn.biz.amislc.aspect.log.builder.common.BasePersistentLogBuilder;
import com.zmn.biz.amislc.model.entity.AmislcOperateLog;
import com.zmn.common.dto2.AMISResponseDTO;
import com.zmn.common.dto2.ResponseDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Amislc持久化日志构建器
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/13 13:55
 **/
public class AmislcPersistentLogBuilder extends BasePersistentLogBuilder<AmislcOperateLog> {
    private static Integer getAppId(Map<String, Object> inputParamMap) {
        Integer appId = NumberUtil.parseInt(Optional.ofNullable(inputParamMap.get("appId")).orElse("0").toString(), 0);
        // 无直接传递appId参数
        if (Objects.isNull(appId) || appId <= 0) {
            for (Object o : inputParamMap.values()) {
                final String str = JSON.toJSONString(o);
                if (!JSONUtil.isTypeJSONObject(str)) {
                    continue;
                }
                // 从参数值中获取appId
                appId = JSON.parseObject(str).getInteger("appId");
                if (Objects.isNull(appId)) {
                    continue;
                }
                break;
            }
        }
        return appId;
    }

    @Override
    public boolean isResultSuccess(Object result) {
        if (result instanceof AMISResponseDTO) {
            return Objects.equals(AMISResponseDTO.SUCCESS, ((AMISResponseDTO<?>) result).getStatus());
        } else if (result instanceof ResponseDTO) {
            return ((ResponseDTO<?>) result).isSuccess();
        }
        return false;
    }

    @Override
    protected AmislcOperateLog buildPersistentLog(
            LogEnvTypeEnum envType,
            LogObjTypeEnum objType,
            LogPersistent logPersistent,
            Map<String, Object> inputParamMap,
            Object outputResult,
            boolean isResultSuccess
    ) {
        // 操作日志数据
        final AmislcOperateLog log = new AmislcOperateLog();
        log.setEnvType(envType.getEnvType());
        log.setObjType(objType.getType());
        log.setObjTypeName(objType.getName());
        log.setOperateType(logPersistent.value().getType());
        log.setOperateParam(convertMapParamToString(inputParamMap));

        // 填充appId（如果有）
        log.setAppId(getAppId(inputParamMap));

        log.setOperateResult(JSON.toJSONString(outputResult));
        log.setOperateStatus(isResultSuccess ? 1 : 2);
        log.setCreateTime(LocalDateTime.now());

        final String objIdParamName = objType.getObjIdParamName();
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

}
