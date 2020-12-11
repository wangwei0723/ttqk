package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_tiktok_account")
public class TtTiktokAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String tiktokName;//账号
    private String tiktokUserName;//账号
    private Integer tagId;//标签ID
    private Integer follow;//'关注数'
    private Integer fans;//粉丝数
    private Integer videos;//视频数
    private Integer isV;//是否大V  字典类别dv  0不是  1是
    private Integer sendFollow;//发起关注数量
    private Integer backFollow;//回关数量
    private Integer userId;//加粉账号
    private Integer isBack;//是否回关 0， 1回关
    private Integer isDel;//是否删除  长时间不回关则删除
    private Integer source;//粉丝来源  0自来   >0 主动添加 大v号
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;

}
