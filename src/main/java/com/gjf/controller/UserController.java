package com.gjf.controller;

import com.gjf.config.UploadProperties;
import com.gjf.config.UrlProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.mapper.UserMapper;
import com.gjf.model.ResultBean;
import com.gjf.model.School;
import com.gjf.model.User;
import com.gjf.service.SchoolFactory;
import com.gjf.utils.PasswordHash;
import com.gjf.utils.StringUtil;
import com.gjf.validator.certification.school.BaseSchool;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    private UploadProperties uploadProperties;

    @RequestMapping(value = "users/new", method = RequestMethod.POST)
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "请求用户表单（json字符串name）", required = true, dataType = "User")
    public ResultBean register(@RequestBody @Valid User user, Errors errors) throws Exception {
        Map<String,String> errMsg = new HashMap<>(5);
        if (errors.hasErrors()) {
            errMsg = errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
        }

        BaseSchool school= SchoolFactory.getInstance(user.getSchool());
        String realName = school.simulateLogin(user.getNumber(),user.getPassword());
        if (realName!=null && realName.length()>0) {
            user.setHeadImg("defaultHeader.png");
            user.setStatus(new Byte("0"));
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

    @RequestMapping(value = "users", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户详细信息", notes = "根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    public ResultBean putUser(@RequestBody User user) {
        return  ResultBean.ok(userMapper.updateByPrimaryKey(user));
    }


    @RequestMapping(value = "user/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户头像", notes = "更新头像")
    @ApiImplicitParam(name = "headerImg", value = "", required = true, dataType = "User")
    public ResultBean putHeaderImg(MultipartFile headerImg,@PathVariable Long userId,Date timeModified){
        String path;
        try {
            path = saveUserHeader(headerImg,userId,uploadProperties.getHeaderImgFolder());
        } catch (IOException e) {
            return ResultBean.error(ExceptionEnum.UPLOAD_ERROR);
        }
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",userId);
        map.put("headerImg",path);
        map.put("timeModified",timeModified);
        userMapper.updateUserHeader(map);
        return ResultBean.ok(path);
    }

    @RequestMapping(value = "user/", method = RequestMethod.PUT)
    @ApiOperation(value = "更新user", notes = "更新信息")
    public ResultBean updateUserStatus(@RequestBody User user) throws Exception{
        String pwd = user.getPassword();
        if (!StringUtil.isBlank(pwd)){
            user.setPassword(PasswordHash.createHash(pwd));
        }
         userMapper.updateByPrimaryKey(user);
         return ResultBean.ok();
    }


    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    public User getUser(@PathVariable Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @GetMapping("user/list")
    @ApiOperation(value = "获取用户列表")
    public ResultBean getUserList(int page,int limit,String searchKey,String searchValue) {
        Map<String,Object> params = new HashMap<>(4);
        logger.info("page=========>"+((page-1)*limit)+"\t"+"limit==========>"+limit);
        params.put("page",(page-1)*limit);
        params.put("limit",limit);
        if (!StringUtil.isBlank(searchKey,searchValue)){
            params.put("searchKey",searchKey);
            params.put("searchValue",searchValue);
        }
        return ResultBean.ok(userMapper.selectRenderAll(params));
    }

    /**
     * @param file 上传头像
     * @param uid  上传用户的id
     * @return 上传图片的relativePath 形式为{"uid+File.separator+imgName.[jpg|png|其他格式]"}
     * 对应数据库中图片的src = relativePath
     * @throws IOException
     */
    private String saveUserHeader(MultipartFile file, Long uid, String folderType) throws IOException {
        StringBuffer relativePath;
        if (file.isEmpty()) {
            throw new IOException("文件大小不能为空");
        }
        byte[] bytes = file.getBytes();
        relativePath = new StringBuffer();
        relativePath.append(uid).append("/")
                .append(file.getOriginalFilename());
        Path path = Paths.get(folderType, relativePath.toString());
        System.out.println(path);
        if (!Files.exists(path)) {
            if (!Files.exists(path.getParent())) {
                Files.createDirectory(path.getParent());
            }
            Files.createFile(path);
        }
        Files.write(path, bytes);
        return relativePath.toString();
    }
}
