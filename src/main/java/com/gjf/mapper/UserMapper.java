package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User>{
    User findUserByName(String name);
}