package com.zoufanqi.aspect.log.anno;

import com.zmn.biz.amislc.aspect.log.enums.LogOperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 持久化日志（日志落库）
 * 默认不自动落库，如果有落库需要在方法上添加该注解即可
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 10:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPersistent {
    LogOperateTypeEnum value();

    boolean persistentSuccess() default true;

    boolean persistentFail() default false;

}
