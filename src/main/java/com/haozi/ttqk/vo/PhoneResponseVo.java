package com.haozi.ttqk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PhoneResponseVo {
    @ApiModelProperty(value = "总数")
    private Integer totalNum;
    @ApiModelProperty(value = "手机列表")
    private List<PhoneVo> phoneList;

}
