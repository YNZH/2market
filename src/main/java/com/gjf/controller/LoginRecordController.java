package com.gjf.controller;

import com.gjf.mapper.LoginRecordMapper;
import com.gjf.model.ResultBean;
import com.gjf.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/06/02
 * Time   : 10:25
 */
@RestController
@RequestMapping("/api/")
public class LoginRecordController {
    private Logger logger = LoggerFactory.getLogger(LoginRecordController.class);

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @GetMapping("loginRecord/list")
    @ApiOperation(value = "获取用户登陆日志")
    public ResultBean getUserList(int page, int limit,String startDate,String endDate,String userName) {
        if(StringUtil.isBlank(startDate)){
            startDate = null;
        }else{
            startDate+=" 00:00:00";
        }
        if(StringUtil.isBlank(endDate)){
            endDate = null;
        }else{
            endDate+=" 23:59:59";
        }
        if(StringUtil.isBlank(userName)){
            userName = null;
        }
        Map<String,Object> params = new HashMap<>(5);
        logger.info("page=========>"+((page-1)*limit)+"\t"+"limit==========>"+limit);
        params.put("page",(page-1)*limit);
        params.put("limit",limit);
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        params.put("userName",userName);
        return ResultBean.ok(loginRecordMapper.getLoginRecordList(params));
    }
}

