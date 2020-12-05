package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PhoneVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "手机编号")
    private String phoneId;
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;
}
