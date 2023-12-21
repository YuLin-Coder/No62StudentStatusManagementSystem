package com.djj.utils;

//权限不足异常类
public class PermissionException extends RuntimeException {

    public PermissionException(String message) {
        super(message);
    }
}
