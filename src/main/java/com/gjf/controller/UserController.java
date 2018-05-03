package com.gjf.controller;

import com.gjf.exception.ExceptionEnum;
import com.gjf.mapper.UserMapper;
import com.gjf.model.ResultBean;
import com.gjf.model.School;
import com.gjf.model.User;
import com.gjf.service.SchoolFactory;
import com.gjf.utils.PasswordHash;
import com.gjf.validator.certification.school.BaseSchool;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/18
 * Time   : 16:56
 */
@RestController
@RequestMapping("/api/")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "users/new", method = RequestMethod.POST)
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "请求用户表单（json字符串na'me）", required = true, dataType = "User")
    public ResultBean register(@RequestBody @Valid User user, Errors errors) throws Exception {
        Map<String,String> errMsg = new HashMap<>();
        if (errors.hasErrors()) {
            errMsg = errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
        }

        if (userMapper.findUserByName(user.getNickname())==null){
            return ResultBean.error(ExceptionEnum.DUPLICATED_NAME);
        }

        BaseSchool school= SchoolFactory.getInstance(user.getSchool());
        String realName = school.simulateLogin(user.getNumber(),user.getPassword());
        if (realName!=null && realName.length()>0) {
            user.setPassword(PasswordHash.createHash(user.getPassword()));
            userMapper.insert(user);
            return ResultBean.ok();
        }else {
            errMsg.put("学号",ExceptionEnum.NO_STUDENT.getMsg());
            return ResultBean.error(ExceptionEnum.PARAMETER_ERROR,errMsg);
        }
    }


    @RequestMapping(value = "users/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    public void deleteUser(@PathVariable Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    public void putUser(@RequestBody User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    public User getUser(@PathVariable Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @GetMapping("users/list")
    @ApiOperation(value = "获取用户列表")
    public List<User> getUserList() {
        return userMapper.selectAll();
    }

}
