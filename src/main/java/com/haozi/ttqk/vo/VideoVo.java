package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVo {
    @ApiModelProperty(value = "视频名称")
    private String name;//视频名称
    @ApiModelProperty(value = "视频地址")
    private String path;//视频地址
    @ApiModelProperty(value = "视频封面图片地址")
    private String faceImgPath;//视频封面图片地址
    @ApiModelProperty(value = "tiktok账号（一般为email）")
    private String userId;//email
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "上传状态 0未上传 1已上传")
    private Integer uploadState;//上传状态 0未上传 1已上传
    @ApiModelProperty(value = "点赞数")
    private Integer great;//点赞数
    @ApiModelProperty(value = "评论数")
    private Integer comment;//评论数
    @ApiModelProperty(value = "上传时间")
    private Date sendTime;//上传时间



}
