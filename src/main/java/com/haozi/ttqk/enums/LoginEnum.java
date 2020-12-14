package com.haozi.ttqk.enums;

public enum LoginEnum {
    NOT_LOGIN(1,"请先登录"),
    TOKEN_INVALID(2,"token已失效，请重新登录"),
    ;
    private Integer code;
    private String msg;

    LoginEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
