package com.zoufanqi.redis.common;

/**
 * key的枚举类型，会自动在key的前面追加类型字符
 *
 * @author: ZOUFANQI
 * @create: 2022-05-06 16:19
 **/
public enum RedisKeyTypeEnum {
    /**
     * key-value
     */
    KV,
    /**
     * hash
     */
    HASH,
    /**
     * list
     */
    LIST,
    /**
     * set
     */
    SET,
    /**
     * zset
     */
    ZSET,
    /**
     * geo
     */
    GEO,
    /**
     * stream
     */
    STREAM
}