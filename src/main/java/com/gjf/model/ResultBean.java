package com.gjf.model;

import com.gjf.exception.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: GJF
 * @Date : 2018/04/21
 * Time   : 21:17
 */
@Data
@Builder
public class ResultBean<T> {
    public final static int NI_LOGIN = 1;
    public final static int SUCCESS = 0;
    public final static int UNKNOW_ERROR = -1;

    private Integer code;
    private String msg;
    private T data;

    /**
     * 返回成功，传入返回体具体出參
     * @param t
     * @return
     */
    public static <T> ResultBean ok(T t) {
        return ResultBean.builder()
                .code(0)
                .msg("success")
                .data(t)
                .build();
    }

    public static ResultBean ok() {
        return ResultBean.builder()
                .code(0)
                .msg("success")
                .data(null)
                .build();
    }

    /**
     *
     * @param code 错误代码
     * @param msg  错误信息
     * @return
     */
    public static ResultBean error(int code,String msg){
        return ResultBean.builder()
                .code(code)
                .msg(msg)
                .build();
    }

    /**
     *
     * @param exceptionEnum 返回自定义异常
     * @return
     */
    public static ResultBean error(ExceptionEnum exceptionEnum){
        return ResultBean.builder()
                .code(exceptionEnum.getCode())
                .msg(exceptionEnum.getMsg())
                .build();
    }

    /**
     *
     * @param exceptionEnum 返回自定义异常
     * @return
     */
    public static <T> ResultBean error(ExceptionEnum exceptionEnum,T t){
        return ResultBean.builder()
                .data(t)
                .code(exceptionEnum.getCode())
                .msg(exceptionEnum.getMsg())
                .build();
    }

    /**
     *
     * @param exceptionEnum     the  exception json to write
     */
    public static String exceptionEnum2Json(ExceptionEnum exceptionEnum){
        return  "{\"code\":"+exceptionEnum.getCode()+",\"msg\":\""+exceptionEnum.getMsg()+"\"}";

    }
}
