package com.zoufanqi.redis;

import com.zoufanqi.redis.common.RedisDynamicParamDef;
import com.zoufanqi.redis.common.RedisHandler;
import com.zoufanqi.redis.common.RedisKeyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * redis使用规范枚举
 *
 * @author: ZOUFANQI
 * @create: 2022-05-06 17:25
 **/
public enum RedisKeyEnum {
    /**
     * 组织架构数据
     */
    ORG(new RedisHandler(
            RedisKeyEnum.REDIS_TPL_NAME, "org", RedisKeyTypeEnum.KV, Duration.ofHours(2), null, "",
            new RedisDynamicParamDef(String.class, "methodName"),
            new RedisDynamicParamDef(String.class, "param md5")
    )),
    /**
     * 成员数据
     */
    MEMBER(new RedisHandler(
            RedisKeyEnum.REDIS_TPL_NAME, "member", RedisKeyTypeEnum.KV, Duration.ofHours(2), null, "",
            new RedisDynamicParamDef(String.class, "methodName"),
            new RedisDynamicParamDef(String.class, "param md5")
    ));

    public final static String REDIS_KEY_COMMON_PREFIX = "bos";

    private final static String REDIS_TPL_NAME = "default";
    private final RedisHandler redisHandler;

    RedisKeyEnum(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

    public static String getKeyPartDelim() {
        return RedisHandler.getKeyPartDelim();
    }

    public RedisHandler getHandler() {
        return redisHandler;
    }

    /**
     * 从spring中初始化redis信息
     */
    @Component
    private static class RedisTplInitFromSpring {
        @Autowired
        private StringRedisTemplate redisTemplate;

        @PostConstruct
        public void init() {
            RedisHandler.registerRedisTpl(RedisKeyEnum.REDIS_TPL_NAME, this.redisTemplate);
        }
    }
}
