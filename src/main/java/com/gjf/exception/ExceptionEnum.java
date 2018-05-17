package com.gjf.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: GJF
 * @Date : 2018/05/01
 * Time   : 17:23
 */

public enum ExceptionEnum {

    UNKNOW_ERROR(-1, "未知错误"),
    UNKNOW_USER_ERROR(1, "非法用户"),
    INVALID_TOKEN(2,"无效的Token"),
    EMPTY_TOKEN(3,"Token为空"),
    PARAMETER_ERROR(4, "参数错误"),
    NO_STUDENT(5,"学号验证失败"),
    INVALID_ACCOUNT(6,"账户或者密码错误"),
    DUPLICATED_KEY(7,"重复主键"),
    UPLOAD_ERROR(8,"上传错误");
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
