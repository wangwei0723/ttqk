package com.haozi.ttqk.enums;

public enum EnvironmentEnum {
    PRO("pro"),
    TEST("test")
    ;

    EnvironmentEnum(String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    private String env;
}
