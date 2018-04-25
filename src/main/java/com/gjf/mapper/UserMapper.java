package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper extends BaseMapper<User>{

}