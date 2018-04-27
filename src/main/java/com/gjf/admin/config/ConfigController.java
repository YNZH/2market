package com.gjf.admin.config;

import com.gjf.mapper.SchoolMapper;
import com.gjf.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: GJF
 * @Date : 2018/04/26
 * Time   : 9:54
 */
@RequestMapping("/api/config")
public class ConfigController {
    @Autowired
    private SchoolMapper schoolMapper;

    @GetMapping("/school/add")
    public void addSchool(@RequestBody School school){
        schoolMapper.insert(school);
    }
}
