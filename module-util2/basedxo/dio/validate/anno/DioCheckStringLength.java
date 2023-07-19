package com.zmn.biz.amislc.common.basedxo.dio.validate.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检测字符串长度
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/13 17:12
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DioCheckStringLength {
    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
