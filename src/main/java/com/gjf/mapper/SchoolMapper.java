package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.School;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SchoolMapper extends BaseMapper<School>{

}