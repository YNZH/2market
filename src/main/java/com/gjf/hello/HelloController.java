package com.gjf.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: GJF
 * @Date : 2018/04/17
 * Time   : 9:24
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index(){
        return "Greeting from 高晋峰";
    }
}
