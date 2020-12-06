package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskTrainUserVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "点赞概率 0-100")
    private Integer great;//点赞概率 0-100
    @ApiModelProperty(value = "评论概率 0-100")
    private Integer comment;//评论概率 0-100
    @ApiModelProperty(value = "浏览评论概率 0-100")
    private Integer seeComment;//浏览评论概率 0-100
    @ApiModelProperty(value = "点赞评论概率 0-100")
    private Integer greatComment;//点赞评论概率 0-100
    @ApiModelProperty(value = "浏览评论时间 秒数")
    private Integer seeCommentTime;//浏览评论时间 秒数
    @ApiModelProperty(value = "观看视频秒数")
    private Integer seeVideoTime;//观看视频秒数
    @ApiModelProperty(value = "每天运行时间  单位 小时")
    private Integer runTimeDay;//每天运行时间  单位 小时



}
