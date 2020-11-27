package com.haozi.ttqk.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserMenuVo {
    private Integer id;
    private Integer typeId;
    private String type;
    private Integer parentId;
    private String keyId;
    private String val;
    private List<ChildMenuVo> childrenMenuList;
}
