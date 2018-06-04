package com.gjf.controller;

import com.gjf.config.TokenProperties;
import com.gjf.config.UploadProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.mapper.LoginRecordMapper;
import com.gjf.mapper.UserMapper;
import com.gjf.model.LoginRecord;
import com.gjf.model.ResultBean;
import com.gjf.model.User;
import com.gjf.security.annotation.PreAuthorize;
import com.gjf.security.el.ELTest;
import com.gjf.service.SchoolFactory;
import com.gjf.utils.JwtKit;
import com.gjf.utils.PasswordHash;
import com.gjf.utils.UserAgentKit;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.TIMEOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: GJF
 * @Date : 2018/04/28
 * Time   : 16:56
 */
@RestController
@RequestMapping("/api/")
public class AuthorizeController {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
    private final UserMapper userMapper;
    private final TokenProperties tokenProperties;
    private final UploadProperties uploadProperties;
    private final LoginRecordMapper loginRecordMapper;
    private final StringRedisTemplate stringRedisTemplate;


    @Autowired
    public AuthorizeController(UserMapper userMapper, TokenProperties tokenProperties, UploadProperties uploadProperties, LoginRecordMapper loginRecordMapper,StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.tokenProperties = tokenProperties;
        this.uploadProperties = uploadProperties;
        this.loginRecordMapper = loginRecordMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("session/new")
    @ApiOperation(value = "创建token", notes = "一般用于浏览器登陆请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)})
    public ResultBean login(@RequestParam("nickname") String nickname, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            stringRedisTemplate.opsForValue().set("JWT-SESSION-"+user.getId(),accessToken,tokenProperties.getExpireTime(), TimeUnit.MILLISECONDS);
            addLoginRecord(request,user.getId());
            return ResultBean.ok(userInfo);
        }else {
            return ResultBean.error(ExceptionEnum.INVALID_ACCOUNT);
        }
    }

    @PreAuthorize("hhh")
    @GetMapping("/test")
    public String  test(){
        ELTest elTest = new ELTest();
        return elTest.test();
    }

    @PostMapping("/session/valid")
    public ResultBean validSession(){
        return ResultBean.ok();
    }

    private void addLoginRecord(HttpServletRequest request,Long userId){
        LoginRecord loginRecord = new LoginRecord();
        UserAgentKit agentKit = new UserAgentKit(request);
        loginRecord.setUserId(userId);
        loginRecord.setIpAddress(agentKit.getIpAddr());
        loginRecord.setDevice(agentKit.getDevice());
        loginRecord.setBrowserType(agentKit.getBrowser());
        loginRecord.setOsName(agentKit.getOS());
        loginRecord.setCreateTime(new Date());
        loginRecordMapper.insert(loginRecord);
    }
}
