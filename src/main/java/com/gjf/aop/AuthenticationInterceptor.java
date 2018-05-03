package com.gjf.aop;

import com.gjf.annotation.LoginRequired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 16:05
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod  handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginRequired loginRequiredAnnotation = method.getAnnotation(LoginRequired.class);
        if(loginRequiredAnnotation!=null){
            String accessToken = request.getParameter("token");
            if (accessToken==null){

            }
        }
        return true;

    }
}
