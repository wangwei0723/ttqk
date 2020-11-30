package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_tag")
public class TtTag {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String tagValue;
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
