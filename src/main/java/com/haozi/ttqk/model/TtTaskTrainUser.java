package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_task_train_user")
public class TtTaskTrainUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer tagId;
    private Integer great;//点赞概率 0-100
    private Integer comment;//评论概率 0-100
    private Integer seeComment;//浏览评论概率 0-100
    private Integer greatComment;//点赞评论概率 0-100
    private Integer seeCommentTime;//浏览评论时间 秒数
    private Integer seeVideoTime;//观看视频秒数
    private Integer runTimeDay;//每天运行时间  单位 小时
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;

}
