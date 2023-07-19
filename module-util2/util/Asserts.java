package com.zmn.biz.amislc.utils;

import com.zmn.biz.amislc.BizAmislcException;
import com.zmn.common.constant.StatusConsts;
import com.zmn.common.dto2.ResponseDTO;
import com.zmn.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 异常断言
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/5 08:57
 */
@Slf4j
public class Asserts {
    /**
     * 对象为null校验
     *
     * @param obj         校验对象
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void mustNull(Object obj, String failMsg, Object... failMsgArgs) {
        if (obj != null) {
            throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
        }
    }

    /**
     * 对象不为null校验
     *
     * @param obj         校验对象
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void notNull(Object obj, String failMsg, Object... failMsgArgs) {
        if (obj == null) {
            throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
        }
    }

    /**
     * 集合不为空/null校验
     *
     * @param coll        校验集合
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void notNull(Collection<?> coll, String failMsg, Object... failMsgArgs) {
        if (coll == null || coll.isEmpty()) {
            throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
        }
    }

    /**
     * 字符串不为空/null校验
     *
     * @param content     校验内容
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void notBlank(String content, String failMsg, Object... failMsgArgs) {
        if (StringUtils.isBlank(content)) {
            throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
        }
    }

    /**
     * 集合元素是否为空
     *
     * @param collection  集合
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2022/9/29 16:56
     */
    public static void notEmpty(Collection<?> collection, String failMsg, Object... failMsgArgs) {
        notEmpty(collection, StatusConsts.ERROR_PARAMS, failMsg, failMsgArgs);
    }

    /**
     * 集合元素是否为空
     *
     * @param collection  集合
     * @param statusConst 错误码
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2022/9/29 16:56
     */
    public static void notEmpty(Collection<?> collection, int statusConst, String failMsg, Object... failMsgArgs) {
        if (collection == null || collection.isEmpty()) {
            throw new BizAmislcException(new ErrorCode(statusConst, buildMsg(failMsg, failMsgArgs)));
        }
    }

    /**
     * 业务匹配校验
     *
     * @param matches     匹配业务
     * @param statusCode  不匹配时抛出错误码
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void matches(boolean matches, int statusCode, String failMsg, Object... failMsgArgs) {
        if (matches) {
            return;
        }
        throw new BizAmislcException(new ErrorCode(statusCode, buildMsg(failMsg, failMsgArgs)));
    }

    public static void matches(boolean matches, String failMsg, Object... failMsgArgs) {
        if (matches) {
            return;
        }
        throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
    }

    /**
     * 业务参数匹配校验
     *
     * @param matches     匹配业务
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void paramMatches(boolean matches, String failMsg, Object... failMsgArgs) {
        if (matches) {
            return;
        }
        throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
    }

    /**
     * 参数错误
     *
     * @param failMsg     错误信息; 如：error msg: %s
     * @param failMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void paramError(String failMsg, Object... failMsgArgs) {
        throw new BizAmislcException(new ErrorCode(StatusConsts.ERROR_PARAMS, buildMsg(failMsg, failMsgArgs)));
    }

    /**
     * 自定义错误异常
     *
     * @param statusCode   错误码
     * @param errorMsg     错误信息; 如：error msg: %s
     * @param errorMsgArgs 错误信息中动态参数
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void error(int statusCode, String errorMsg, Object... errorMsgArgs) {
        throw new BizAmislcException(new ErrorCode(statusCode, buildMsg(errorMsg, errorMsgArgs)));
    }

    /**
     * 自定义错误异常
     *
     * @param message 错误信息
     * @param cause   异常
     * @author zoufanqi
     * @since 2023/6/5 09:22
     */
    public static void error(String message, Throwable cause) {
        throw new BizAmislcException(message, cause);
    }

    private static String buildMsg(String failMsg, Object... failMsgArgs) {
        if (failMsgArgs == null || failMsgArgs.length == 0) {
            return failMsg;
        } else {
            return String.format(failMsg, failMsgArgs);
        }
    }

    /**
     * 校验dubbo响应信息
     *
     * @param response 响应对象
     * @author zoufanqi
     * @since 2023/6/5 10:20
     */
    public static void validateDubboResponse(ResponseDTO<?> response) {
        if (Objects.isNull(response)) {
            log.warn("dubbo响应信息为null");
        }
        if (!response.isSuccess()) {
            log.error("请求dubbo接口异常：{}", response);
            error(response.getStatus(), response.getMessage());
        }
    }
}
