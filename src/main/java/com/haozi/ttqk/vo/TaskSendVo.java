package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskSendVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "任务类别 0  养号评论 1,截流评论  2私信内容")
    private Integer type;//任务类别
    @ApiModelProperty(value = "评论ID")
    private Integer commentId;//评论ID
    @ApiModelProperty(value = "评论内容")
    private String comment;//评论内容
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;//发送时间
    @ApiModelProperty(value = "停止时间")
    private Date stopTime;//停止时间
    @ApiModelProperty(value = "发送次数")
    private Integer sendTimes;//发送次数
    @ApiModelProperty(value = "间隔时间")
    private Integer intervals;//'间隔时间'
}
