package com.gjf.controller;

import com.gjf.config.TokenProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.mapper.UserMapper;
import com.gjf.model.ResultBean;
import com.gjf.model.User;
import com.gjf.service.SchoolFactory;
import com.gjf.utils.JwtKit;
import com.gjf.utils.PasswordHash;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/28
 * Time   : 16:56
 */
@RestController
@RequestMapping("/api/")
public class AuthorizeController {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenProperties tokenProperties;

    @PostMapping("session/new")
    @ApiOperation(value = "创建token", notes = "一般那用登陆请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)})
    public ResultBean login(@RequestParam("nickname") String nickname, @RequestParam("password") String password) throws Exception{
        User user = userMapper.findUserByName(nickname);
        if(PasswordHash.validatePassword(password,user.getPassword())){
            String accessToken = JwtKit.generateToken(user.getId().toString(),user.getNickname(), tokenProperties.getExpireTime());
            Map<String,String> userInfo = new HashMap<>();
            userInfo.put("token",accessToken);
            userInfo.put("userId",user.getId().toString());
            return ResultBean.ok(userInfo);
        }else {
            return ResultBean.error(ExceptionEnum.INVALID_ACCOUNT);
        }
    }

    @GetMapping("/test")
    public String  test(){
        String name =SchoolFactory.getInstance("武汉理工大学").simulateLogin("0121410870525","030157");
        return "\"姓名\":\""+name+"\"";
    }
}
