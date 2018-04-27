package com.gjf.controller;

import com.gjf.mapper.UserMapper;
import com.gjf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/18
 * Time   : 16:56
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register.do")
    public ResponseEntity<?> register(@RequestBody @Valid User user, Errors errors) {
        System.out.println(user.toString());

        if (errors.hasErrors()) {
            Map<String,String> errorInfo = new HashMap<>(10);
            for (ObjectError error:errors.getAllErrors()
                 ) {
                errorInfo.put(error.getObjectName(),error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        int ret = userMapper.insert(user);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/login.do")
    public ResponseEntity<?> login(@RequestParam String name,@RequestParam String password){
        return ResponseEntity.ok("success");
    }

}
