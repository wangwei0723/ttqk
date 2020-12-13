package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_task_send_log")
public class TtTaskSendLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer taskId;
    private Integer videoId;//发送给哪个视频
    private Long accountId;//发送给哪个账号
    private String userId;//发送着ID
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
