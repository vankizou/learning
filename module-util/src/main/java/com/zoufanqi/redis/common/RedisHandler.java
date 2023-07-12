package com.zoufanqi.redis.common;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: ZOUFANQI
 * @create: 2022-05-06 17:21
 **/
@Slf4j
public class RedisHandler {
    // ======================== 公共属性 =========================
    /**
     * redis空值标识符
     */
    public static final String REDIS_NULL_VALUE = "$NULL_VALUE";
    /**
     * key每部分之间的分隔符
     */
    private static final String KEY_PART_DELIM = ":";
    /**
     * spring-redis模版
     */
    private static final Map<String, StringRedisTemplate> STRING_REDIS_TEMPLATE_MAP = new HashMap<>();
    /**
     * redis是否开启（仅标记，RedisOpsHandler子类有相关操作实现）
     */
    private static boolean isRedisOpen = false;
    /**
     * 本类中所有key的统一前缀（配置文件中可配置）
     */
    private static String keyPrefix = "";

    // ====================== 内部属性 =======================
    /**
     * redis template
     */
    private final String redisTplName;
    /**
     * redis key前缀
     */
    private final String redisKeyPrefix;
    /**
     * redis-key数据存储类型
     */
    private final RedisKeyTypeEnum redisKeyType;
    private final String redisKeyTypeStr;
    /**
     * 默认动态过期时间，若无过期时间，传null即可
     */
    private final Duration defaultExpireTime;
    /**
     * 默认空值过期时间，若空值无需过期时间，传null即中
     */
    private final Duration defaultExpireTimeOfNullValue;
    /**
     * 描述
     */
    private final String desc;
    /**
     * redis-key后面动态部分的数据类型
     */
    private final RedisDynamicParamDef[] redisDynamicParamDefs;

    public RedisHandler(
            String redisTplName,
            String redisKeyPrefix,
            RedisKeyTypeEnum redisKeyType,
            String desc,
            RedisDynamicParamDef... redisDynamicParamDefs
    ) {
        this(redisTplName, redisKeyPrefix, redisKeyType, null, null, desc, redisDynamicParamDefs);
    }

    public RedisHandler(
            String redisTplName,
            String redisKeyPrefix,
            RedisKeyTypeEnum redisKeyType,
            Duration defaultExpireTime,
            Duration defaultExpireTimeOfNullValue,
            String desc,
            RedisDynamicParamDef... redisDynamicParamDefs
    ) {
        this.redisTplName = redisTplName;
        this.redisKeyPrefix = redisKeyPrefix.toLowerCase(Locale.ROOT);
        this.redisKeyType = redisKeyType;
        this.redisKeyTypeStr = redisKeyType.name().toLowerCase(Locale.ROOT);
        this.defaultExpireTime = defaultExpireTime;
        this.defaultExpireTimeOfNullValue = defaultExpireTimeOfNullValue;
        this.desc = desc;
        this.redisDynamicParamDefs = redisDynamicParamDefs;
    }

    public static void registerRedisTpl(String redisTplName, StringRedisTemplate stringRedisTemplate) {
        if (StrUtil.isNotBlank(redisTplName) && stringRedisTemplate != null) {
            STRING_REDIS_TEMPLATE_MAP.put(redisTplName, stringRedisTemplate);
        }
    }

    public static boolean isRedisOpening() {
        return isRedisOpen;
    }

    /**
     * key分隔符
     */
    public static String getKeyPartDelim() {
        return KEY_PART_DELIM;
    }

    /**
     * 自动设置空值过期时间
     */
    public boolean expireNullVal(BoundKeyOperations<String> ops) {
        return this.expire(ops, this.getDefaultExpireTimeOfNullValue());
    }

    /**
     * 自动设置过期时间
     */
    public boolean expire(BoundKeyOperations<String> ops) {
        return this.expire(ops, this.getDefaultExpireTime());
    }

    /**
     * 指定过期时间
     */
    public boolean expire(BoundKeyOperations<String> ops, Duration duration) {
        if (!isRedisOpening() || duration == null || duration.getSeconds() <= 0) {
            return false;
        }
        return Boolean.TRUE.equals(ops.expire(duration.getSeconds(), TimeUnit.SECONDS));
    }

    public <V> V boundValueOpsHandler(
            Supplier<V> originalDataSupplier,
            Function<String, V> cacheDataDeserializer,
            Object... keyParts
    ) {
        return this.boundValueOpsHandler(
                originalDataSupplier,
                JSON::toJSONString,
                cacheDataDeserializer,
                keyParts
        );
    }

    public <V> V boundValueOpsHandler(
            Supplier<V> originalDataSupplier,
            Function<V, String> cacheDataSerializer,
            Function<String, V> cacheDataDeserializer,
            Object... keyParts
    ) {
        return this.boundValueOpsHandler(
                true,
                null,
                originalDataSupplier,
                cacheDataSerializer,
                cacheDataDeserializer,
                false,
                keyParts
        );
    }

    /**
     * 针对kv格式的数据提供的模版方法
     *
     * @param tryOpenCache          是否开启缓存
     * @param expireTime            指定失效时间
     * @param originalDataSupplier  获取源数据的函数（如：从数据库中获取数据）
     * @param cacheDataSerializer   序列化方法
     * @param cacheDataDeserializer 反序列化方法
     * @param checkServiceExpire    检测值是否过期（仅当缓存数据继承自RedisDto）
     * @param keyParts              动态key值
     * @param <V>                   序列化类
     * @return 对象数据
     */
    public <V> V boundValueOpsHandler(
            Boolean tryOpenCache,
            Duration expireTime,
            Supplier<V> originalDataSupplier,
            Function<V, String> cacheDataSerializer,
            Function<String, V> cacheDataDeserializer,
            Boolean checkServiceExpire,
            Object... keyParts
    ) {
        tryOpenCache = (tryOpenCache == null || tryOpenCache) && isRedisOpen;
        final BoundValueOperations<String, String> ops = tryOpenCache ? this.boundValueOps(keyParts) : null;
        final String dataStr = tryOpenCache ? ops.get() : null;
        // 缓存有数据处理
        outIf:
        if (Objects.nonNull(dataStr)) {
            if (Objects.equals(dataStr, REDIS_NULL_VALUE)) {
                // 缓存中数据标记为空值
                return null;
            } else if (Objects.isNull(cacheDataDeserializer)) {
                return null;
            } else {
                // 缓存非空数据
                log.info("hit cache [{}], data: {}", ops.getKey(), dataStr.length() > 1000 ? dataStr.substring(0, 1000) + "..." : dataStr);
                try {
                    final V cacheData = cacheDataDeserializer.apply(dataStr);
                    if (Objects.isNull(cacheData)) {
                        log.warn("缓存数据序列化失败，忽略缓存数据，直接查询数据源，key: {}", ops.getKey());
                        break outIf;
                    }
                    // 检测缓存值是否过期
                    if (checkServiceExpire != null &&
                            checkServiceExpire &&
                            (cacheData instanceof RedisDto) &&
                            ((RedisDto) cacheData).isExpired()) {
                        break outIf;
                    }
                    return cacheData;
                } catch (Exception e) {
                    log.warn("缓存数据序列化失败（不影响业务，继续请求源数据）：" + dataStr, e);
                }
            }
        }
        // 缓存无数据处理
        if (Objects.isNull(originalDataSupplier)) {
            return null;
        }
        final V data = originalDataSupplier.get();
        if (Objects.isNull(ops)) {
            return data;
        }
        final boolean hasValAndSerializer = Objects.nonNull(cacheDataSerializer) && (Objects.isNull(expireTime) || expireTime.getSeconds() > 0);
        if (Objects.isNull(data)) {
            final boolean flag = (Objects.isNull(expireTime) || expireTime.getSeconds() > 0)
                    && Objects.nonNull(this.getDefaultExpireTimeOfNullValue());
            if (flag) {
                // 源值为空值，设置空值过期时间
                ops.set(REDIS_NULL_VALUE);
                this.expire(ops, this.getDefaultExpireTimeOfNullValue());
            }
        }
        // 有值 + 有序列化
        else if (hasValAndSerializer) {
            final String dataCache;
            if (data instanceof String) {
                dataCache = (String) data;
            } else {
                dataCache = cacheDataSerializer.apply(data);
            }
            ops.set(dataCache);

            if (Objects.nonNull(expireTime)) {
                this.expire(ops, expireTime);
            } else if (Objects.nonNull(this.getDefaultExpireTime())) {
                this.expire(ops, this.getDefaultExpireTime());
            }
        }
        return data;
    }

    public BoundValueOperations<String, String> boundValueOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.KV) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.KV.name());
        }
        return getStringRedisTemplate().boundValueOps(this.buildVariableKey(keyParts));
    }

    public BoundHashOperations<String, String, String> boundHashOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.HASH) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.HASH.name());
        }
        return getStringRedisTemplate().boundHashOps(this.buildVariableKey(keyParts));
    }

    public BoundListOperations<String, String> boundListOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.LIST) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.LIST.name());
        }
        return getStringRedisTemplate().boundListOps(this.buildVariableKey(keyParts));
    }

    public BoundSetOperations<String, String> boundSetOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.SET) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.SET.name());
        }
        return getStringRedisTemplate().boundSetOps(this.buildVariableKey(keyParts));
    }

    public BoundZSetOperations<String, String> boundZsetOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.ZSET) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.ZSET.name());
        }
        return getStringRedisTemplate().boundZSetOps(this.buildVariableKey(keyParts));
    }

    public BoundGeoOperations<String, String> boundGeoOps(Object... keyParts) {
        if (this.redisKeyType != RedisKeyTypeEnum.GEO) {
            error("redis key type not match, need %s, found: %s", this.redisKeyType.name(), RedisKeyTypeEnum.GEO.name());
        }
        return getStringRedisTemplate().boundGeoOps(this.buildVariableKey(keyParts));
    }

    /**
     * 构建动态key，【参数数量和类型】须和定义保持一致
     *
     * @param keyParts key动态数据
     * @return redis key
     */
    public String buildVariableKey(Object... keyParts) {
        // 校验定义的参数数量与实际传入的参数数量
        if (this.redisDynamicParamDefs != null) {
            if (keyParts == null || keyParts.length != this.redisDynamicParamDefs.length) {
                error("redis key params not match");
            }
        }
        if (keyParts == null || keyParts.length == 0) {
            return this.redisKeyPrefix;
        }
        // key builder
        final StringBuilder keyBuilder = new StringBuilder(128);
        keyBuilder.append(getKeyPrefix());

        for (int i = 0, len = keyParts.length; i < len; i++) {
            Object p = keyParts[i];
            if (p == null) {
                p = "";
            } else {
                // 校验传入参数的类型与定义的类型是否一致
                assert this.redisDynamicParamDefs != null;
                if (!p.getClass().equals(this.redisDynamicParamDefs[i].getType())) {
                    error(
                            "param [%s] type error, need [%s]",
                            p.toString(),
                            this.redisDynamicParamDefs[i].getType().getSimpleName()
                    );
                }
            }
            keyBuilder.append(KEY_PART_DELIM).append(p);
        }
        return keyBuilder.substring(0, keyBuilder.length());
    }

    public String getKeyPrefix() {
        final StringBuilder keyBuilder = new StringBuilder(128);
        if (StrUtil.isNotBlank(keyPrefix)) {
            // redis-key统一前缀
            keyBuilder.append(keyPrefix).append(KEY_PART_DELIM);
        }
        keyBuilder.append(this.redisKeyTypeStr);
        keyBuilder.append(KEY_PART_DELIM);
        keyBuilder.append(this.redisKeyPrefix);
        return keyBuilder.toString();
    }

    public RedisKeyTypeEnum getRedisKeyType() {
        return redisKeyType;
    }

    public Duration getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public Duration getDefaultExpireTimeOfNullValue() {
        return defaultExpireTimeOfNullValue;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        final StringRedisTemplate stringRedisTemplate = STRING_REDIS_TEMPLATE_MAP.get(this.redisTplName);
        if (Objects.isNull(stringRedisTemplate)) {
            error("not found redis template: %s", this.redisTplName);
        }
        return stringRedisTemplate;
    }

    private void error(String failMsg, Object... params) {
        throw new IllegalArgumentException(String.format(failMsg, params));
    }

    /**
     * 从spring中初始化redis信息
     */
    @Component
    private static class RedisInfoInitFromSpring {
        // TODO
        // @Value("${spring.redis-common.open:true}")
        private String redisOpen;
        // @Value("${spring.redis-common.key-prefix:" + RedisKeyEnum.REDIS_KEY_COMMON_PREFIX + "}")
        private String keyPrefix;

        @PostConstruct
        public void init() {
            RedisHandler.isRedisOpen =
                    !("0".equals(redisOpen) ||
                            "off".equalsIgnoreCase(redisOpen) ||
                            "false".equalsIgnoreCase(redisOpen));
            RedisHandler.keyPrefix = keyPrefix;
        }
    }
}
