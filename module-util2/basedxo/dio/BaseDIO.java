package com.zmn.biz.amislc.common.basedxo.dio;

import com.alibaba.fastjson.JSON;
import com.zmn.biz.amislc.common.basedxo.dio.validate.DioValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 入参基类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 13:54
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDIO implements Serializable {
    @ApiModelProperty(value = "非必填字段计数处理，入参：<非必填字段总数，非必填字段空值数量>", hidden = true)
    protected BiConsumer<Integer, Integer> notRequireCountConsumer;

    @ApiModelProperty(value = "是否检测字段", hidden = true)
    protected Boolean checkFields;

    /**
     * 校验失败动作
     *
     * @param failCond  失败条件
     * @param fieldName 字段名
     * @param failMsg   失败信息
     * @author zoufanqi
     * @since 2023/6/12 17:07
     */
    public static void failed(boolean failCond, String fieldName, String failMsg) {
        DioValidator.failed(failCond, fieldName, failMsg);
    }

    public static String trimString(String attr) {
        return trimString(attr, null);
    }

    /**
     * 字符串修剪
     *
     * @param attr           字段值
     * @param defaultIfBlank 为空默认值
     * @return 修剪后的值
     * @author zoufanqi
     * @since 2023/6/30 15:01
     */
    public static String trimString(String attr, String defaultIfBlank) {
        return (Objects.isNull(attr) || "".equals(attr = attr.trim())) ? defaultIfBlank : attr;
    }

    public static <T> T defaultIfNull(T val, T defaultIfValNull) {
        return Objects.isNull(val) ? defaultIfValNull : val;
    }


    public void validate() {
        if (Objects.isNull(checkFields) || checkFields) {
            DioValidator.validateFields(this, notRequireCountConsumer);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
