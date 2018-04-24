package com.gjf.service.base;

import org.apache.ibatis.session.SqlSession;

/**
 * @Author: GJF
 * @Date : 2018/04/19
 * Time   : 15:01
 */
public class BaseDao {
    private final SqlSession sqlSession;

    public BaseDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
