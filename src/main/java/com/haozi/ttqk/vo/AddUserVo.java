package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddUserVo {
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String pwd;
}
