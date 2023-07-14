package com.zmn.biz.amislc.common.dro.app.basic;

import com.zmn.biz.amislc.common.drobase.BaseDRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/3 17:47
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppDRO extends BaseDRO {

    @ApiModelProperty(value = "应用组ID")
    private Integer appGroupId;

    @ApiModelProperty(value = "应用ID")
    private Integer appId;

    @ApiModelProperty(value = "应用key")
    private String appKey;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updater;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
