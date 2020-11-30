package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TagVo {
    @ApiModelProperty(value = "标签ID")
    private Integer id;
    @ApiModelProperty(value = "标签值")
    private String tagValue;
}
