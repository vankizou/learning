package com.zmn.biz.amislc.common.diobase.validate.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串正则校验
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/13 17:12
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DioCheckStringRegex {

    /**
     * 正则
     *
     * @return 正则
     * @author zoufanqi
     * @since 2023/7/14 10:33
     */
    String regex();

    /**
     * 样例
     *
     * @return 样例
     * @author zoufanqi
     * @since 2023/7/14 10:40
     */
    String example() default "";
}
