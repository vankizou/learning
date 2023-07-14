package com.zmn.biz.amislc.common.dio.app.modify;

import com.alibaba.fastjson.JSON;
import com.zmn.biz.amislc.common.diobase.BaseAddDIO;
import com.zmn.biz.amislc.common.diobase.validate.anno.DioCheckStringRegex;
import com.zmn.biz.amislc.common.diobase.validate.anno.DioDoStringTrimmed;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

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
public class AppAddDIO extends BaseAddDIO {
    public static final Pattern APP_KEY_PATTERN = Pattern.compile("");

    private static final long serialVersionUID = -7180707423799025725L;

    @ApiModelProperty(value = "应用组ID", required = true)
    private Integer appGroupId;

    @DioDoStringTrimmed
    @DioCheckStringRegex(regex = "[a-z-]{1,55}(-frontend)", example = "[demo]-frontend（长度<64）")
    @ApiModelProperty(value = "应用key", required = true)
    private String appKey;

    @DioDoStringTrimmed
    @ApiModelProperty(value = "应用名", required = true)
    private String appName;

    @ApiModelProperty(value = "描述")
    private String remark;


    public static void main(String[] args) {
        final AppModifyDIO dio = AppModifyDIO.builder().operator("111").appId(1).appGroupId(1).appName("123").build();
        dio.validate();
        System.out.println(JSON.toJSONString(dio));
    }
}
