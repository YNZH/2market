package com.gjf.mapper.base;

import java.util.List;
/**
 * @Author: GJF
 * @Date : 2018/04/25
 * Time   : 15:35
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Long pkId);

    int insert(T record);

    T selectByPrimaryKey(Long pkId);

    List<T> selectAll();

    int updateByPrimaryKey(T record);
}
