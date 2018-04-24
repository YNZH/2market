package com.gjf.handler;

import io.jsonwebtoken.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 18:12
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    Map<String, String> body = new HashMap<>(2);

    @ExceptionHandler(value = {UnsupportedJwtException.class, MalformedJwtException.class,
            SignatureException.class, ExpiredJwtException.class,IllegalArgumentException.class})
    public ResponseEntity<?> errorToken(Exception exce) {
        this.body.clear();
        this.body.put("error", "invalid token");
        return ResponseEntity.badRequest().body(body);
    }
}
