package com.gjf.mapper;

import com.gjf.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: GJF
 * @Date : 2018/04/19
 * Time   : 11:15
 */
@Mapper
public interface UserMapper {
    /**
     * insert a user record to database
     * @param user user
     * @return user unique id
     */
    int saveUser(User user);
}
