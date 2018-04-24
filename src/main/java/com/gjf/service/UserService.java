package com.gjf.service;

import com.gjf.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * @Author: GJF
 * @Date : 2018/04/24
 * Time   : 17:01
 */
@Service
public class UserService {

    private final SqlSession sqlSession;

    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int insertUser(User user){
        return sqlSession.selectOne("saveUser");
    }
}
