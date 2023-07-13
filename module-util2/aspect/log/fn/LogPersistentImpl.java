package com.zmn.biz.amislc.aspect.log.fn;

import com.zmn.biz.amislc.aspect.log.anno.LogPersistent;
import com.zmn.biz.amislc.aspect.log.enums.LogOperateTypeEnum;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/5 08:57
 **/
@RequiredArgsConstructor
public class LogPersistentImpl implements LogPersistent {
    private final LogOperateTypeEnum operateType;

    @Override
    public LogOperateTypeEnum value() {
        return this.operateType;
    }

    @Override
    public boolean persistentSuccess() {
        return true;
    }

    @Override
    public boolean persistentFail() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
