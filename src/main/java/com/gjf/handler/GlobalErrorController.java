//package com.gjf.handler;
//
//import jdk.nashorn.internal.objects.Global;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
//import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Assert;
//import org.springframework.util.MimeTypeUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: GJF
// * @Date : 2018/05/02
// * Time   : 10:40
// */
//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
//public class GlobalErrorController extends BasicErrorController{
//
//
//    public GlobalErrorController(ErrorAttributes errorAttributes,
//                                 ErrorProperties errorProperties) {
//        super(errorAttributes, errorProperties);
//    }
//
//    public GlobalErrorController(ErrorAttributes errorAttributes,
//                                 ErrorProperties errorProperties,
//                                 List<ErrorViewResolver> errorViewResolvers) {
//        super(errorAttributes, errorProperties, errorViewResolvers);
//    }
//
//    @RequestMapping(produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
//    @ResponseBody
//    public String errorTextPlan(HttpServletRequest request) {
//
//        Map<String, Object> body = getErrorAttributes(request,
//                isIncludeStackTrace(request, MediaType.ALL));
//        body.put("status", getStatus(request));
//        return body.toString();
//
//    }
//}
