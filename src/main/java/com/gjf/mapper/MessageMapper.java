package com.gjf.mapper;

import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MessageMapper extends BaseMapper<Message>{
    List<Message> getOfflineMessage(Long userId);
    List<Map<String,Object>> getHistory(Long userId);
    void updateToReaded(Long userId);

}