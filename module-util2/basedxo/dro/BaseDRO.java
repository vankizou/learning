package com.zmn.biz.amislc.common.basedxo.dro;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 响应数据基类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/13 08:46
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDRO implements Serializable {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
