package com.zoufanqi.aspect.log.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Function;

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
    APP_GROUP(1, "应用组", "appGroupId", clsName -> clsName.contains("APPGROUP")),
    /**
     * 应用
     */
    APP(2, "应用", "appId", clsName -> clsName.contains("APP") && !clsName.contains("APPGROUP") && !clsName.contains("APPVERSION")),
    /**
     * 应用版本
     */
    APP_VERSION(3, "应用版本", "appVersionId", clsName -> clsName.contains("APPVERSION")),
    /**
     * 页面
     */
    PAGE(11, "页面", "pageId", clsName -> clsName.contains("PAGE") && !clsName.contains("PAGEVERSION")),
    /**
     * 页面版本
     */
    PAGE_VERSION(12, "页面版本", "pageVersionId", clsName -> clsName.contains("PAGEVERSION")),
    ;

    private final Integer type;

    private final String name;

    /**
     * id参数名，如：应用相关接口，主键id参数名为appId
     */
    private final String objIdParamName;

    /**
     * 通过admin/dubbo类名进行匹配
     * dubbo类名全大写 -> 是否匹配
     */
    private final Function<String, Boolean> interfaceClsMatches;

    LogObjTypeEnum(Integer type, String name, String objIdParamName, Function<String, Boolean> interfaceClsMatches) {
        this.type = type;
        this.name = name;
        this.objIdParamName = objIdParamName;
        this.interfaceClsMatches = interfaceClsMatches;
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
