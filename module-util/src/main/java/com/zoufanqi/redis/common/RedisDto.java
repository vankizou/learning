package com.zoufanqi.redis.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author: ZOUFANQI
 * @create: 2022-07-18 14:09
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class RedisDto {
    /**
     * 失效时间（毫秒）; 二选一
     */
    private Long expireOn;

    public boolean isExpired() {
        return expireOn == null || expireOn < System.currentTimeMillis();
    }
}
