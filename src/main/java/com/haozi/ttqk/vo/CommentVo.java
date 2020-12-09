package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;
    @ApiModelProperty(value = "评论内容")
    private String comment;
    @ApiModelProperty(value = "评论类型 0  养号评论 1,截流评论  2私信内容")
    private Integer type;//0  养号评论 1,截流评论  2私信内容
}
