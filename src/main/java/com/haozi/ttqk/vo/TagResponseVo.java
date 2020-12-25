package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TagResponseVo {
    @ApiModelProperty(value = "总数")
    private Integer totalNum;
    @ApiModelProperty(value = "标签列表")
    private List<TagVo> tagList;

}
