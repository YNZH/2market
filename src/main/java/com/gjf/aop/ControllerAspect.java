package com.gjf.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: GJF
 * @Date : 2018/05/01
 * Time   : 18:15
 */
@Aspect
@Component
public class ControllerAspect {
    private static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(public * com.gjf.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //log something
        logger.info("===================================request before step into method================================");
        logger.info("url = {}", request.getRequestURI());
        logger.info("Request.Method = {}", request.getMethod());
        logger.info("ip = {}", request.getRemoteAddr());
        logger.info("method to invoke = {}", joinPoint.getSignature().getDeclaringTypeName() + "=====>"
                + joinPoint.getSignature().getName());
        logger.info("args = {}",joinPoint.getArgs());
    }

    @After("log()")
    public void logAfter() {
        logger.info("===================================response after method invoke================================");
    }

    @AfterReturning(pointcut = "log()", returning = "object")
    public void logReturn(Object object) {
        logger.info("===================================response value================================");
        logger.info("response = {}",object.toString());
    }

}
