package com.zmn.biz.amislc.common.diobase;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * 分页查询入参基类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/10 13:54
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BasePageDIO extends BaseQueryDIO {

    @ApiModelProperty(value = "当前第几页，起始值为：1", required = true)
    protected Integer pageIndex;

    @ApiModelProperty(value = "每页显示多少条", required = true)
    protected Integer pageSize;

    @ApiModelProperty(value = "是否查询总数", notes = "默认：true")
    protected Boolean searchCount = true;

    @Override
    public void validate() {
        super.validate();
        failed(pageIndex <= 0, "pageIndex", "必须>0");
        failed(pageSize <= 0, "pageSize", "必须>0");
        if (Objects.isNull(searchCount)) {
            searchCount = true;
        }
    }

}
