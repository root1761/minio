package com.lnsoft.minio.enums;

public enum ResponseCode {
    SUCCESS("请求成功");
    private String message;
    ResponseCode(String message) {
        this.message = message;
    }

    ResponseCode() {
    }

    public String getMessage() {
        return message;
    }

}
