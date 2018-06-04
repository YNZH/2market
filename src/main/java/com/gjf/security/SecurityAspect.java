package com.gjf.security;

import com.gjf.security.annotation.PreAuthorize;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 21:19
 */
@Aspect
@Component
public class SecurityAspect {
    @Around("@annotation(authorize)")
    public Object aroundPreAuthorize(ProceedingJoinPoint point, PreAuthorize authorize) throws Throwable{
        boolean isOk = handleAuthorize(point,authorize);
        if (isOk){
            return point.proceed();
        }
        throw new RuntimeException("表达式["+authorize.value()+"]验证失败");
    }

    private boolean handleAuthorize(ProceedingJoinPoint point,PreAuthorize authorize){

        return false;
    }

    private EvaluationContext getContext(){
        return new StandardEvaluationContext();
    }
}
