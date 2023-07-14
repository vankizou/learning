package com.zoufanqi.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * common模块工具类（尽量减少依赖）
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 14:05
 **/
@Slf4j
public class CommonUtil {
    private static final Map<Class<?>, List<Field>> BEAN_FIELD_CACHE_MAP = new HashMap<>(128);

    private static final Map<Class<?>, Map<String, Method>> BEAN_METHOD_CACHE_MAP = new HashMap<>(1024);

    /**
     * 获取class以及父类所有属性
     *
     * @param cls cls
     * @return 属性列表
     * @author zoufanqi
     * @since 2023/6/6 15:02
     */
    public static List<Field> getAllFields(Class<?> cls) {
        List<Field> fields = BEAN_FIELD_CACHE_MAP.get(cls);
        if (Objects.nonNull(fields) && !fields.isEmpty()) {
            return fields;
        }
        fields = new ArrayList<>(32);
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        BEAN_FIELD_CACHE_MAP.put(cls, fields);
        return fields;
    }

    public static String getGetterName(String fieldName) {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static String getSetterName(String fieldName) {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
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
        Map<String, Method> methodMap = BEAN_METHOD_CACHE_MAP.get(beanCls);
        if (Objects.isNull(methodMap)) {
            BEAN_METHOD_CACHE_MAP.put(beanCls, (methodMap = new HashMap<>(32)));
        }
        final String cacheKey = methodName + "(" + (paramTypes.length > 0 ? JSON.toJSONString(paramTypes) : "") + ")";
        try {
            // 使用过后的方法进行缓存
            if (methodMap.containsKey(cacheKey)) {
                return methodMap.get(cacheKey);
            } else {
                final Method method = beanCls.getMethod(methodName, paramTypes);
                methodMap.put(cacheKey, method);
                return method;
            }
        } catch (NoSuchMethodException e) {
            methodMap.put(cacheKey, null);
            return null;
        }
    }

}
