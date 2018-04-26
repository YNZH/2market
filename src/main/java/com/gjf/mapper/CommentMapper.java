package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}