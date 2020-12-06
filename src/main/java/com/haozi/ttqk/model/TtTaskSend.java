package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_task_send")
public class TtTaskSend {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer tagId;//标签ID
    private Integer type;//任务类别
    private Integer commentId;//评论ID
    private Date sendTime;//发送时间
    private Date stopTime;//停止时间
    private Integer sendTimes;//发送次数
    private Integer intervals;//'间隔时间'
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
