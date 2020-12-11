package com.haozi.ttqk.model;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_tiktok_user")
public class TiktokUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String userId;//用户标识（email）
    private String name;//用户名
    private String tiktokId;//抖音账户ID
    private String pwd;//密码
    private String phone;//绑定手机好
    private String img;//账号图片
    private String img2;//账号动图
    private Integer tagId;//标签ID
    private Integer status;//0 养号状态 1运营状态
    private Integer merchantId;//所属商户
    private String introduction;//简介
    private String phoneId;//手机设备ID
    private String url;//个人主页
    private Integer updateState;//是否在手机上更新  0未更新  1更新
    private Integer look;//观看视频数
    private Integer great;
    private Integer comment;//评论数
    private Integer follow;//关注数
    private Integer fans;//粉丝数
    private Integer videos;//视频数量
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;


}
