package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    List<Map<String,Object>> listCommentsByGoodsId(Long goodsId);
}