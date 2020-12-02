package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_video")
public class TtVideo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String name;//视频名称
    private String path;//视频地址
    private String faceImgPath;//视频封面图片地址
    private String userId;//email
    private Integer tagId;//标签ID
    private Integer uploadState;//上传状态 0未上传 1已上传
    private Integer great;//点赞数
    private Integer comment;//评论数
    private Date sendTime;//上传时间
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;

}
