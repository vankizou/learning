package com.zmn.biz.amislc.common.dio.app.modify;

import com.zmn.biz.amislc.common.diobase.BaseModifyDIO;
import com.zmn.biz.amislc.common.diobase.validate.anno.DioCheckStringRegex;
import com.zmn.biz.amislc.common.diobase.validate.anno.DioDoStringTrimmed;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/30 14:29
 **/
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppModifyDIO extends BaseModifyDIO {
    private static final long serialVersionUID = -7180707423799025725L;

    @ApiModelProperty(value = "应用ID", required = true)
    private Integer appId;

    @ApiModelProperty(value = "应用组ID")
    private Integer appGroupId;

    @DioDoStringTrimmed
    @DioCheckStringRegex(regex = "[a-z-]{1,55}(-frontend)", example = "[demo]-frontend（长度<64）")
    @ApiModelProperty(value = "应用key")
    private String appKey;

    @DioDoStringTrimmed
    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "描述")
    private String remark;

}
