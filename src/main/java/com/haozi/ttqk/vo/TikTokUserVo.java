package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TikTokUserVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "用户标识(email等)")
    private String userId;//用户标识（email）
    @ApiModelProperty(value = "用户名")
    private String name;//用户名
    @ApiModelProperty(value = "抖音账户ID")
    private String tiktokId;//抖音账户ID
    @ApiModelProperty(value = "密码")
    private String pwd;//密码
    @ApiModelProperty(value = "绑定手机号")
    private String phone;//绑定手机号
    @ApiModelProperty(value = "账号图片url")
    private String img;//账号图片url
    @ApiModelProperty(value = "账号动图url")
    private String img2;//账号动图url
    @ApiModelProperty(value = "标签ID")
    private Integer tagId;//标签ID
    @ApiModelProperty(value = "标签值")
    private String tagValue;//标签值
    @ApiModelProperty(value = "账号状态 0 养号状态 1运营状态")
    private Integer status;//账号状态 0 养号状态 1运营状态
    @ApiModelProperty(value = "所属商户")
    private Integer merchantId;//所属商户
    @ApiModelProperty(value = "简介")
    private String introduction;//简介
    @ApiModelProperty(value = "手机设备ID")
    private String phoneId;//手机设备ID
    @ApiModelProperty(value = "个人主页")
    private String url;//个人主页
    @ApiModelProperty(value = "是否在手机上更新  0未更新  1更新")
    private Integer updateState;//是否在手机上更新  0未更新  1更新
    @ApiModelProperty(value = "观看视频数")
    private Integer look;//观看视频数
    @ApiModelProperty(value = "。。。")
    private Integer great;
    @ApiModelProperty(value = "评论数")
    private Integer comment;//评论数
    @ApiModelProperty(value = "关注数")
    private Integer follow;//关注数
    @ApiModelProperty(value = "粉丝数")
    private Integer fans;//粉丝数
    @ApiModelProperty(value = "视频数量")
    private Integer videos;//视频数量
    @ApiModelProperty(value = "状态 0有效 1无效")
    private Integer isDelete;
}
