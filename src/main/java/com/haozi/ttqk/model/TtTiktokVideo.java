package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_tiktok_video")
public class TtTiktokVideo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String name;//视频名
    private String videoKey;//视频名称前20位md5
    private Integer tagId;//标签ID
    private Long accountId;//tiktok上传者
    private Integer great;//点赞数
    private String date;//日期
    private Integer comment;//评论数
    private Integer tagOk;//标签是否确认  0标签错误  1，标签正确
    private Integer sendComm;//发送评论数量
    private Integer greatGl;//点赞概率 -1-100
    private Integer comment_gl;//评论概率-1—100
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;

}
