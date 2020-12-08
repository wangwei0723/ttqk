package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TiktokVideoVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "视频名")
    private String name;//视频名称
    @ApiModelProperty(value = "视频videoKey")
    private String videoKey;//视频名称前20位md5
    @ApiModelProperty(value = "视频videoKey")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "tiktok上传者")
    private Long accountId;//tiktok上传者
    @ApiModelProperty(value = "点赞数")
    private Integer great;//点赞数
    @ApiModelProperty(value = "日期")
    private String date;//日期
    @ApiModelProperty(value = "评论数")
    private Integer comment;//评论数
    @ApiModelProperty(value = "标签是否确认  0标签错误  1，标签正确")
    private Integer tagOk;//标签是否确认  0标签错误  1，标签正确
    @ApiModelProperty(value = "发送评论数量")
    private Integer sendComm;//发送评论数量
    @ApiModelProperty(value = "点赞概率 -1-100")
    private Integer greatGl;//点赞概率 -1-100
    @ApiModelProperty(value = "评论概率-1—100")
    private Integer comment_gl;//评论概率-1—100



}
