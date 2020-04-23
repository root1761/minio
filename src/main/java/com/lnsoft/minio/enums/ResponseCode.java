package com.lnsoft.minio.enums;

public enum ResponseCode {
    FILE_IS_NOT_EXIST("文件不存在！！！"),
    BUCKET_IS_NOT_EXIST("桶不存在！！！"),
    FILENAME_IS_NULL("文件不能为空！！！"),
    BUCKETNAME_IS_NULL("桶名不能为空！！！"),
    BUCKET_CREATE_FAILED("桶创建失败！！！"),
    BUCKET_DELETE_FAILED("桶删除失败！！！"),
    BUCKET_IS_EXIST("桶已经存在！！！"),
    FILE_DELETE_FAILED("文件删除失败！！！");
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
