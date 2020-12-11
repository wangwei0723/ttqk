package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskTrainUserLogVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "userId")
    private Integer userId;
    @ApiModelProperty(value = "videoKey")
    private String videoKey;
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "点赞")
    private Integer isgreat;
    @ApiModelProperty(value = "评论")
    private Integer iscomment;
    @ApiModelProperty(value = "评论时间")
    private String commentTime;

}
