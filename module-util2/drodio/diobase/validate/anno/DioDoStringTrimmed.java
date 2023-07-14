package com.zmn.biz.amislc.common.diobase.validate.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串trim
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/13 17:12
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DioDoStringTrimmed {
}
