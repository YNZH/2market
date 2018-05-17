package com.gjf.controller;

import com.gjf.config.TokenProperties;
import com.gjf.config.UploadProperties;
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

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    @Autowired
    private UploadProperties uploadProperties;

    @PostMapping("session/new")
    @ApiOperation(value = "创建token", notes = "一般用于浏览器登陆请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)})
    public ResultBean login(@RequestParam("nickname") String nickname, @RequestParam("password") String password, HttpServletResponse response) throws Exception{
        User user = userMapper.findUserByName(nickname);
        if(user == null){
            return ResultBean.error(ExceptionEnum.INVALID_ACCOUNT);
        }
        if(PasswordHash.validatePassword(password,user.getPassword())){
            String accessToken = JwtKit.generateToken(user.getId().toString(),user.getNickname(), tokenProperties.getExpireTime());
            Map<String,String> userInfo = new HashMap<>(4);
            Cookie cookie = new Cookie("access_token",accessToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/api/");
            cookie.setMaxAge((int)(JwtKit.tokenProperties.getExpireTime()/1000));
            response.addCookie(cookie);
            userInfo.put("userId",user.getId().toString());
            userInfo.put("nickname",user.getNickname());
            userInfo.put("prefix", uploadProperties.getPrefix());
            userInfo.put("headImg", user.getHeadImg());
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

    @PostMapping("/session/valid")
    public ResultBean validSession(){
        return ResultBean.ok();
    }
}
