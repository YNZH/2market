package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    Long getGoodsCountByUserId(Long userId);
    List<Goods> getGoodsByCategory(String category);
}