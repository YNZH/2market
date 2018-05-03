package com.gjf.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: GJF
 * @Date : 2018/05/01
 * Time   : 17:39
 */

public class GlobalException extends RuntimeException{
    @Getter @Setter private int code;

    public GlobalException(ExceptionEnum defineException){
        super(defineException.getMsg());
        this.code = defineException.getCode();
    }

    public GlobalException(int code, String msg){
        super(msg);
        this.code = code;
    }

}
