package com.haozi.ttqk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name="tt_file")
public class TtFile {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    private String uuid;//文件唯一标识
    private String fileUrl;//文件下载链接
    private String fileName;//文件名称
    private Integer fileType;//文件类型
    private String fileDirectory;//文件存储目录
    private String tiktokId;//tiktok用户ID
    private Integer userId;//文件上传操作人ID
    private Date createdTime;
    private Date updateTime;
    private Integer isDelete;
}
