package com.gjf.mapper;

import com.gjf.model.UserTest;
import java.util.List;

public interface UserTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserTest record);

    UserTest selectByPrimaryKey(Long id);

    List<UserTest> selectAll();

    int updateByPrimaryKey(UserTest record);
}