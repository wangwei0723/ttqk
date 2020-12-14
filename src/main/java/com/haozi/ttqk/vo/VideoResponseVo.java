package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VideoResponseVo {
    @ApiModelProperty(value = "总数")
    private Integer totalNum;
    @ApiModelProperty(value = "视频列表")
    private List<VideoVo> videoList;

}
