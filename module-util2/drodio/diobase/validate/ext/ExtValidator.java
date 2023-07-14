package com.zmn.biz.amislc.common.diobase.validate.ext;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;

@FunctionalInterface
public interface ExtValidator {
    /**
     * 验证逻辑
     *
     * @param entity           对象
     * @param field            字段
     * @param fieldVal         字段值
     * @param apiModelProperty apiModelProperty
     * @return 修改后的值
     * @author zoufanqi
     * @since 2023/7/14 10:28
     */
    Object validate(Object entity, Field field, Object fieldVal, ApiModelProperty apiModelProperty);
}