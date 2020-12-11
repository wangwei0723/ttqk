package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TiktokAccountVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "账号")
    private String tiktokName;//账号
    @ApiModelProperty(value = "UserName")
    private String tiktokUserName;//账号
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "关注数")
    private Integer follow;//'关注数'
    @ApiModelProperty(value = "粉丝数")
    private Integer fans;//粉丝数
    @ApiModelProperty(value = "视频数")
    private Integer videos;//视频数
    @ApiModelProperty(value = "是否大V  字典类别dv  0不是  1是")
    private Integer isV;//是否大V  字典类别dv  0不是  1是
    @ApiModelProperty(value = "发起关注数量")
    private Integer sendFollow;//发起关注数量
    @ApiModelProperty(value = "回关数量")
    private Integer backFollow;//回关数量
    @ApiModelProperty(value = "加粉账号")
    private Integer userId;//加粉账号
    @ApiModelProperty(value = "是否回关 0， 1回关")
    private Integer isBack;//是否回关 0， 1回关
    @ApiModelProperty(value = "是否删除  长时间不回关则删除")
    private Integer isDel;//是否删除  长时间不回关则删除
    @ApiModelProperty(value = "粉丝来源  0自来   >0 主动添加 大v号")
    private Integer source;//粉丝来源  0自来   >0 主动添加 大v号



}
