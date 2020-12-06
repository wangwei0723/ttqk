package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_task_add_fans")
public class TtTaskAddFans {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer tagId;//标签ID
    private Integer addDay;//每天加粉数量
    private Integer delDay;//每天删粉数量
    private Integer delDate;//粉丝时限，超过这个期限的粉丝删除 腾出位置加新粉
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
