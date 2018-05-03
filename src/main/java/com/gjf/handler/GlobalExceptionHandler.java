package com.gjf.handler;

import com.gjf.exception.ExceptionEnum;
import com.gjf.exception.GlobalException;
import com.gjf.model.ResultBean;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 18:12
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResultBean processException(Exception e) {
        logger.info("捕获Controller异常信息=========> "+e.getClass().getName());
        e.printStackTrace();
        if (e instanceof JwtException){
            return ResultBean.error(ExceptionEnum.INVALID_TOKEN);
        }
        if (e instanceof GlobalException) {
            return ResultBean.error(((GlobalException) e).getCode()
                    ,e.getMessage());
        }
        logger.info("未知错误");
        return ResultBean.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
