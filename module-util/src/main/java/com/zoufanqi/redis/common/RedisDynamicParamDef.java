package com.zoufanqi.redis.common;

/**
 * 动态后缀参数信息的定义
 *
 * @author: ZOUFANQI
 * @create: 2022-05-06 16:40
 **/
public class RedisDynamicParamDef {
    private final Class<?> type;
    private final String desc;

    public RedisDynamicParamDef(Class<?> type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Class<?> getType() {
        return type;
    }
}