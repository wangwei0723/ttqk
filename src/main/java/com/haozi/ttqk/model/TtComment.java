package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_comment")
public class TtComment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private Integer tagId;
    private String comment;
    private Integer type;//0  养号评论 1,截流评论  2私信内容
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
