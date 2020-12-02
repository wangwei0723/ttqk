package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileVo {
    @ApiModelProperty(value = "文件下载地址")
    private String fileUrl;
    @ApiModelProperty(value = "视频封面下载地址")
    private String fileFaceUrl;
}
