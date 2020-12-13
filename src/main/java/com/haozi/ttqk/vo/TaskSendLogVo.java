package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskSendLogVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "taskId")
    private Integer taskId;
    @ApiModelProperty(value = "发送给哪个视频")
    private Integer videoId;//发送给哪个视频
    @ApiModelProperty(value = "发送给哪个账号")
    private Long accountId;//发送给哪个账号
    @ApiModelProperty(value = "发送着ID")
    private String userId;//发送着ID
}
