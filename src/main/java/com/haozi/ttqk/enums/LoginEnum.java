package com.haozi.ttqk.enums;

public enum LoginEnum {
    LOGIN_NORMAL(1,"登录状态正常"),
    NOT_LOGIN(2,"请先登录"),
    TOKEN_INVALID(3,"token已失效，请重新登录"),
    ;
    private Integer code;
    private String msg;

    LoginEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
