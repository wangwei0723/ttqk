package com.haozi.ttqk.vo;

import lombok.Data;

@Data
public class ChildMenuVo {
    private Integer id;
    private Integer typeId;
    private String type;
    private Integer parentId;
    private String keyId;
    private String val;
}
