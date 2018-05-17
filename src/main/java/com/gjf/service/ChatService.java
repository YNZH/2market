package com.gjf.service;

import com.gjf.model.Message;
import com.gjf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: GJF
 * @Date : 2018/05/13
 * Time   : 16:12
 */
@Service
public class ChatService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 广播
     */
    public void broadcast(Message message){
        simpMessagingTemplate.convertAndSend("/topic/all", message);
    }

    /**
     * send to special users
     */
    public void send2Users(List<User> users, Message responseMessage){
        users.forEach( id -> simpMessagingTemplate.convertAndSendToUser(String.valueOf(id),"/users",responseMessage));
    }

    /**
     * send to special user
     */
    public void send2User(String userId,Message responseMessage){
        if ("nil".equalsIgnoreCase(stringRedisTemplate.opsForValue().get(userId))){
            // this user is offline

        }else{
            simpMessagingTemplate.convertAndSendToUser(userId,"/message",responseMessage);
        }
    }
}
