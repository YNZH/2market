package com.gjf.service;

import com.gjf.mapper.MessageMapper;
import com.gjf.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 10:54
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> getOfflineMsg(Long userId){
        List<Message> ret = messageMapper.getOfflineMessage(userId);
        messageMapper.updateToReaded(userId);
        return ret;
    }

    public List<Map<String,Object>> getHistoryMsg(Long userId){
        return messageMapper.getHistory(userId);
    }


}
