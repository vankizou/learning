package com.zmn.biz.amislc.aspect.log.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略日志打印入参
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 16:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrintIgnoreOutputResponse {

}
