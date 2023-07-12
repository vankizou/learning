package com.zoufanqi.aspect.log.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/4 17:32
 **/
@Getter
public enum LogEnvTypeEnum {

    /**
     * admin
     */
    ADMIN(1, "admin"),

    /**
     * dubbo
     */
    DUBBO(2, "dubbo");

    private final Integer envType;
    private final String name;

    LogEnvTypeEnum(Integer type, String name) {
        this.envType = type;
        this.name = name;
    }

    public static LogEnvTypeEnum valueOf(Integer type) {
        if (Objects.nonNull(type)) {
            for (LogEnvTypeEnum e : LogEnvTypeEnum.values()) {
                if (type.equals(e.getEnvType())) {
                    return e;
                }
            }
        }
        return null;
    }

}
