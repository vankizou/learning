package com.zoufanqi.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * common模块工具类（尽量减少依赖）
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 14:05
 **/
@Slf4j
public class CommonUtil {
    private static final Map<String, List<Field>> BEAN_FIELD_CACHE_MAP = new HashMap<>(128);

    private static final Map<String, Method> BEAN_METHOD_CACHE_MAP = new HashMap<>(1024);

    /**
     * 校验DIO参数必填项
     * 【io.swagger.annotations.ApiModelProperty】注解中设置【required=true】时触发校验
     *
     * @param bean dio对象
     * @param <T>  泛型
     * @author zoufanqi
     * @since 2023/6/6 15:01
     */
    public static <T> void validateDioFields(T bean, BiConsumer<Integer, Integer> notRequireCountConsumer) {
        final List<Field> allFields = getAllFields(bean.getClass());

        // 非必填字段总数量
        int notRequireTotalCount = 0;
        // 非必填字段无值数量
        int notRequireNonValCount = 0;

        for (Field field : allFields) {
            final ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (Objects.isNull(apiModelProperty)) {
                continue;
            }
            // 必填字段校验
            if (apiModelProperty.required()) {
                validateGetterMethodVal(bean, field, apiModelProperty);
            }
            // 非必填字段计次
            else if (Objects.nonNull(notRequireCountConsumer) && !apiModelProperty.hidden()) {
                notRequireTotalCount++;
                if (Objects.isNull(getFieldValByGetter(bean, field))) {
                    notRequireNonValCount++;
                }
            }
        }

        // 非必填字段计数处理（比如：不允许非必填字段全部为空）
        if (Objects.nonNull(notRequireCountConsumer)) {
            notRequireCountConsumer.accept(notRequireTotalCount, notRequireNonValCount);
        }
    }

    /**
     * 获取class以及父类所有属性
     *
     * @param cls cls
     * @return 属性列表
     * @author zoufanqi
     * @since 2023/6/6 15:02
     */
    public static List<Field> getAllFields(Class<?> cls) {
        final String key = cls.getName();
        List<Field> fields = BEAN_FIELD_CACHE_MAP.get(key);
        if (CollectionUtil.isEmpty(fields)) {
            return fields;
        }
        fields = new ArrayList<>(32);
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        BEAN_FIELD_CACHE_MAP.put(key, fields);
        return fields;
    }


    private static <T> void validateGetterMethodVal(T bean, Field field, ApiModelProperty apiModelProperty) {
        final String fieldName = field.getName();
        final Object methodVal = getFieldValByGetter(bean, field);

        // 注解值
        final String annoVal = StrUtil.isBlank(apiModelProperty.value()) ? "" : String.format(" (%s)", apiModelProperty.value());

        // 校验
        if (methodVal instanceof String) {
            validateActionIfFailed("".equals(((String) methodVal).trim()), fieldName + annoVal, "不能为空");
        } else if (methodVal instanceof Collection) {
            validateActionIfFailed(((Collection<?>) methodVal).isEmpty(), fieldName + annoVal, "不能为空");
        } else {
            validateActionIfFailed(Objects.isNull(methodVal), fieldName + annoVal, "不能为空");
        }
    }

    private static Object getFieldValByGetter(Object bean, Field field) {
        // 通过getter方法获取字段值
        final String fieldName = field.getName();
        Object methodVal = null;
        try {
            final Method getterMethod = getPublicMethod(bean.getClass(), getGetterName(fieldName));
            // 方法调用
            methodVal = getterMethod.invoke(bean);
        } catch (Exception e) {
            log.error("getter方法不存在", e);
        }
        return methodVal;
    }

    /**
     * 校验属性失败动作
     *
     * @param failCond  失败条件
     * @param fieldName 属性名
     * @param failMsg   失败信息
     * @author zoufanqi
     * @since 2023/6/6 15:19
     */
    public static void validateActionIfFailed(boolean failCond, String fieldName, String failMsg) {
        if (failCond) {
            throw new IllegalArgumentException(String.format("[%s] %s", fieldName, failMsg));
        }
    }

    private static String getGetterName(String fieldName) {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    /**
     * 获取公共方法
     *
     * @param beanCls    cls
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 公共方法
     * @author zoufanqi
     * @since 2023/7/11 14:54
     */
    public static Method getPublicMethod(Class<?> beanCls, String methodName, Class<?>... paramTypes) {
        final String cacheKey = beanCls.getName() + "#" + methodName +
                "(" + (paramTypes.length > 0 ? JSON.toJSONString(paramTypes) : "") + ")";
        try {
            // 使用过后的方法进行缓存
            Method method;
            if (Objects.isNull(method = BEAN_METHOD_CACHE_MAP.get(cacheKey))) {
                method = beanCls.getMethod(methodName, paramTypes);
                BEAN_METHOD_CACHE_MAP.put(cacheKey, method);
            }
            return method;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("未找到方法：%s", cacheKey));
        }
    }

}
