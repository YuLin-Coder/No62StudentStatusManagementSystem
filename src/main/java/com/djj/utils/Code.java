package com.djj.utils;

public enum Code {

    SUCCESS("1000", "操作成功"),
    ERROR("1001", "操作失败"),
    NODATA("1002", "暂无数据"),
    LOGIN_ERROR("1003", "用户名或密码错误"),
    NOT_EMPTY("1004", "不能为空");

    private String code;
    private String msg;

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
