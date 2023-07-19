package com.zmn.biz.amislc.common.basedxo.dio.validate.ext;

import com.zmn.biz.amislc.common.basedxo.dio.validate.DioValidator;
import com.zmn.biz.amislc.common.basedxo.dio.validate.anno.DioCheckStringLength;
import com.zmn.biz.amislc.common.basedxo.dio.validate.anno.DioCheckStringRegex;
import com.zmn.biz.amislc.common.basedxo.dio.validate.anno.DioDoStringTrimmed;
import com.zmn.biz.amislc.common.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/14 10:26
 **/
@Slf4j
@Getter
@AllArgsConstructor
public enum ExtValidatorEnum {
    /**
     * 修剪字符串
     */
    DO_STRING_TRIMMED(
            f -> Objects.nonNull(f.getAnnotation(DioDoStringTrimmed.class)) && f.getType() == String.class,
            (entity, field, fieldVal, propAnno) -> {
                // 为null不用处理
                if (Objects.isNull(fieldVal)) {
                    return null;
                }

                // 比较修剪前后的值，如果一致，则不处理
                final String fieldValStr = ((String) fieldVal);
                final String trimmedStr = fieldValStr.trim();
                if (fieldValStr.equals(trimmedStr)) {
                    return null;
                }
                setFieldVal("DO_STRING_TRIMMED", entity, field, trimmedStr);
                return trimmedStr;
            }
    ),
    /**
     * 字段值长度校验
     */
    CHECK_STRING_LENGTH(
            f -> Objects.nonNull(f.getAnnotation(DioCheckStringLength.class)) && f.getType() == String.class,
            (entity, field, fieldVal, propAnno) -> {
                final DioCheckStringLength anno = field.getAnnotation(DioCheckStringLength.class);
                final int len = Objects.isNull(fieldVal) ? 0 : String.valueOf(fieldVal).length();
                DioValidator.failed(
                        len < anno.min() || len > anno.max(),
                        DioValidator.buildFailedFieldName(field.getName(), propAnno),
                        String.format("长度限制：[%d,%d]", anno.min(), anno.max())
                );
                return null;
            }
    ),
    /**
     * 字段值正则校验
     */
    CHECK_STRING_REGEX(
            f -> Objects.nonNull(f.getAnnotation(DioCheckStringRegex.class)) && f.getType() == String.class,
            (entity, field, fieldVal, propAnno) -> {
                // 非必填字段且值为空，则忽略
                final boolean ignore = !propAnno.required() && (Objects.isNull(fieldVal) || "".equals(fieldVal));
                if (ignore) {
                    return null;
                }

                // 获取注解
                final DioCheckStringRegex anno = field.getAnnotation(DioCheckStringRegex.class);
                final String regex = anno.regex();
                if (Objects.isNull(regex) || "".equals(regex)) {
                    return null;
                }

                // 正则对象添加缓存
                Pattern pattern = getPatternCacheMap().get(regex);
                if (Objects.isNull(pattern)) {
                    pattern = Pattern.compile(regex);
                    getPatternCacheMap().put(regex, pattern);
                }

                // 正则匹配
                if (!pattern.matcher((String) fieldVal).matches()) {
                    DioValidator.failed(
                            true,
                            field.getName(),
                            "不符合规范：" + fieldVal +
                                    (
                                            Objects.isNull(anno.example()) ||
                                                    "".equals(anno.example()) ? "" :
                                                    ("，样例：" + anno.example())
                                    )
                    );
                }
                return null;
            }
    ),

    ;

    private final static Map<String, Pattern> PATTERN_CACHE_MAP = new HashMap<>();

    private final Function<Field, Boolean> matches;

    private final ExtValidator validate;

    private static void setFieldVal(String ext, Object entity, Field field, String setVal) {
        // 值前后不一致，则设置修剪后的值
        final Method method = CommonUtil.getPublicMethod(entity.getClass(), CommonUtil.getSetterName(field.getName()), field.getType());
        try {
            method.invoke(entity, setVal);
        } catch (Exception e) {
            log.warn(String.format("设置字段值失败（不影响业务）[%s]：%s", ext, field.getName()), e);
        }
    }

    private static Map<String, Pattern> getPatternCacheMap() {
        return PATTERN_CACHE_MAP;
    }
}
