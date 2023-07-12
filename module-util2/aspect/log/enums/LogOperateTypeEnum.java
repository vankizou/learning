package com.zoufanqi.aspect.log.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 10:28
 **/
@Getter
public enum LogOperateTypeEnum {
    /**
     * 增
     */
    INSERT(1, "添加"),
    /**
     * 删
     */
    DELETE(2, "删除"),
    /**
     * 改
     */
    UPDATE(3, "修改"),
    /**
     * 查
     */
    SELECT(4, "查询"),

    /**
     * 添加或修改自动识别
     */
    INSERT_OR_UPDATE(-1, "添加或修改自动识别");

    private final Integer type;
    private final String name;

    LogOperateTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static LogOperateTypeEnum valueOf(Integer type) {
        if (Objects.nonNull(type)) {
            for (LogOperateTypeEnum e : LogOperateTypeEnum.values()) {
                if (type.equals(e.getType())) {
                    return e;
                }
            }
        }
        return null;
    }
}
