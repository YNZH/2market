package com.gjf.controller;

import com.gjf.mapper.UserMapper;
import com.gjf.service.SchoolFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: GJF
 * @Date : 2018/04/28
 * Time   : 16:56
 */
@RestController
@RequestMapping("/api/")
public class AuthorizeController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("session/new")
    @ApiOperation(value = "创建token", notes = "一般那用登陆请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)})
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {

        return ResponseEntity.ok("success");
    }

    @GetMapping("/test")
    public String  test(){
        String name =SchoolFactory.getInstance("武汉理工大学").simulateLogin("0121410870525","030157");
        return "\"姓名\":\""+name+"\"";
    }
}
