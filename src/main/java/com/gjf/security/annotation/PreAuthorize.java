package com.gjf.security.annotation;

import java.lang.annotation.*;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 20:55
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * sp-el
     * @return {String}
     */
    String value();
}
