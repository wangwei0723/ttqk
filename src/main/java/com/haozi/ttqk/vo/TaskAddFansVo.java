package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskAddFansVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "每天加粉数量")
    private Integer addDay;//每天加粉数量
    @ApiModelProperty(value = "每天删粉数量")
    private Integer delDay;//每天删粉数量
    @ApiModelProperty(value = "粉丝时限，超过这个期限的粉丝删除 腾出位置加新粉")
    private Integer delDate;//粉丝时限，超过这个期限的粉丝删除 腾出位置加新粉
}
