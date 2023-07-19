package com.zmn.biz.amislc.common.basedxo.dio.validate;

import com.zmn.biz.amislc.common.basedxo.dio.validate.ext.ExtValidatorEnum;
import com.zmn.biz.amislc.common.utils.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * common模块工具类（尽量减少依赖）
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 14:05
 **/
@Slf4j
public class DioValidator {

    /**
     * 校验DIO参数必填项
     * 【io.swagger.annotations.ApiModelProperty】注解中设置【required=true】时触发校验
     *
     * @param bean dio对象
     * @param <T>  泛型
     * @author zoufanqi
     * @since 2023/6/6 15:01
     */
    public static <T> void validateFields(T bean, BiConsumer<Integer, Integer> notRequireCountConsumer) {
        final List<Field> allFields = CommonUtil.getAllFields(bean.getClass());

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
                if (Objects.isNull(getFieldValByGetter(bean, field, apiModelProperty))) {
                    notRequireNonValCount++;
                }
            }
        }

        // 非必填字段计数处理（比如：不允许非必填字段全部为空）
        if (Objects.nonNull(notRequireCountConsumer)) {
            notRequireCountConsumer.accept(notRequireTotalCount, notRequireNonValCount);
        }
    }


    private static <T> void validateGetterMethodVal(T bean, Field field, ApiModelProperty apiModelProperty) {
        final String fieldName = field.getName();
        final Object methodVal = getFieldValByGetter(bean, field, apiModelProperty);

        if (Objects.nonNull(methodVal)) {
            // 校验有值但是值不符合
            if (methodVal instanceof String) {
                failed("".equals(((String) methodVal).trim()), buildFailedFieldName(fieldName, apiModelProperty), "不能为空");
            } else if (methodVal instanceof Collection) {
                failed(((Collection<?>) methodVal).isEmpty(), buildFailedFieldName(fieldName, apiModelProperty), "不能为空");
            }
        }
        // 值为空
        else {
            final Class<?> type = field.getType();
            if (Enum.class.isAssignableFrom(type)) {
                failed(true, buildFailedFieldName(fieldName, apiModelProperty), "不能为空，参考：" + Arrays.asList(field.getType().getEnumConstants()));
            } else {
                failed(true, buildFailedFieldName(fieldName, apiModelProperty), "不能为空");
            }
        }
    }

    private static Object getFieldValByGetter(Object bean, Field field, ApiModelProperty apiModelProperty) {
        final String fieldName = field.getName();
        // 通过getter方法获取字段值
        Object methodVal = null;
        try {
            final Method getterMethod = CommonUtil.getPublicMethod(bean.getClass(), CommonUtil.getGetterName(fieldName));
            // 方法调用
            methodVal = getterMethod.invoke(bean);
        } catch (Exception e) {
            log.error("getter方法不存在", e);
        }

        // 扩展注解校验
        for (ExtValidatorEnum ext : ExtValidatorEnum.values()) {
            if (!ext.getMatches().apply(field)) {
                continue;
            }
            final Object methodVal2 = ext.getValidate().validate(bean, field, methodVal, apiModelProperty);
            if (Objects.nonNull(methodVal2)) {
                methodVal = methodVal2;
            }
        }
        return methodVal;
    }

    public static String buildFailedFieldName(String fieldName, ApiModelProperty apiModelProperty) {
        return fieldName + (Objects.isNull(apiModelProperty.value()) ? "" : String.format(" (%s)", apiModelProperty.value().trim()));
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
    public static void failed(boolean failCond, String fieldName, String failMsg) {
        if (failCond) {
            throw new IllegalArgumentException(String.format("[%s] %s", fieldName, failMsg));
        }
    }

}
