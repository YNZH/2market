package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.LoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author GJF
 */
@Mapper
@Repository
public interface LoginRecordMapper extends BaseMapper<LoginRecord>{
    List<Map<String,Object>> getLoginRecordList(Map<String,Object> params);
}