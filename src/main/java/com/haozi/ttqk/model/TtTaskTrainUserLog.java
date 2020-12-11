package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_task_train_user_log")
public class TtTaskTrainUserLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer userId;
    private String videoKey;
    private Integer tagId;
    private Integer isgreat;
    private Integer iscomment;
    private Integer commentTime;
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;

}
