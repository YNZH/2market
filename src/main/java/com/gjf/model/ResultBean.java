package com.gjf.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/21
 * Time   : 21:17
 */
@Data
public class ResultBean<T> {
    public final static int NI_LOGIN = 1;
    public final static int SUCCESS = 0;
    public final static int FAILURE = 2;

    private int code;
    private T data;
}
