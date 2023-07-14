package com.zmn.biz.amislc.common.dio.app.query;

import com.zmn.biz.amislc.common.diobase.BasePageDIO;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/6 14:23
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppQueryDIO extends BasePageDIO {
    private static final long serialVersionUID = 2590910165857725108L;

    @ApiModelProperty(value = "应用组ID")
    private Integer appGroupId;

    @ApiModelProperty(value = "应用ID")
    private Integer appId;

    @ApiModelProperty(value = "应用key")
    private String appKey;

    @ApiModelProperty(value = "应用名")
    private String appName;

}
