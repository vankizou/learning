package com.zmn.biz.amislc.common.diobase;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.function.BiConsumer;

/**
 * 修改入参基类
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
public abstract class BaseModifyDIO extends BaseDIO {
    private final static BiConsumer<Integer, Integer> NOT_REQUIRE_COUNT_CONSUMER = (totalNum, nullValNum) ->
            failed(nullValNum > 0 && nullValNum >= totalNum, "非必填字段", "不能全部为空");

    @ApiModelProperty(value = "操作人", required = true, hidden = true)
    protected String operator;

    @Override
    public void validate() {
        // 更新信息时，非必填字段至少要有一个值
        this.notRequireCountConsumer = NOT_REQUIRE_COUNT_CONSUMER;
        super.validate();
    }
}
