package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User>{
    User findUserByName(String name);
    void updateUserHeader(Map map);
}