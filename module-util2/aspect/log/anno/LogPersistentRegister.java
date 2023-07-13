package com.zmn.biz.amislc.aspect.log.anno;

import com.zmn.biz.amislc.aspect.log.enums.LogObjTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 持久化日志接口注册
 * <p>
 * 使用样例：
 * <p>
 * \@RestController
 * \@RequestMapping("/demo")
 * \@LogRegister(LogObjTypeEnum.DEMO)
 * public class DemoController {
 * ...
 * }
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/7 10:39
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPersistentRegister {
    /**
     * 日志主体类型
     *
     * @return objType
     * @author zoufanqi
     * @since 2023/7/12 18:20
     */
    LogObjTypeEnum value();
}
