package com.zmn.biz.amislc.aspect.log.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 需要持久化dubbo接口操作日志的在此扩展枚举
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 10:28
 **/
@Getter
public enum LogObjTypeEnum {
    /**
     * 应用组
     */
    APP_GROUP(1, "应用组", "appGroupId"),
    /**
     * 应用
     */
    APP(2, "应用", "appId"),
    /**
     * 应用版本
     */
    APP_VERSION(3, "应用版本", "appVersionId"),
    /**
     * 页面
     */
    PAGE(11, "页面", "pageId"),
    /**
     * 页面版本
     */
    PAGE_VERSION(12, "页面版本", "pageVersionId"),
    ;

    private final Integer type;

    private final String name;

    /**
     * id参数名，如：应用相关接口，主键id参数名为appId
     */
    private final String objIdParamName;

    LogObjTypeEnum(Integer type, String name, String objIdParamName) {
        this.type = type;
        this.name = name;
        this.objIdParamName = objIdParamName;
    }

    public static LogObjTypeEnum valueOf(Integer type) {
        if (Objects.nonNull(type)) {
            for (LogObjTypeEnum e : LogObjTypeEnum.values()) {
                if (type.equals(e.getType())) {
                    return e;
                }
            }
        }
        return null;
    }

}
