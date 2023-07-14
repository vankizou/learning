package com.zmn.biz.amislc.common.diobase;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 添加数据基类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/6 13:54
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseAddDIO extends BaseDIO {

    @ApiModelProperty(value = "操作人", required = true, hidden = true)
    protected String operator;

}
