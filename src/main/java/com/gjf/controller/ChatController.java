package com.gjf.controller;

import com.gjf.model.Message;
import com.gjf.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * @Author: GJF
 * @Date : 2018/05/13
 * Time   : 11:12
 */
@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping(value = "/hello")
    @SendTo("/topic/greeting")
    public void reply(Message message) throws Exception {
        log.info(message.toString());
    }

    @MessageMapping(value = "/message")
    @SendToUser("/message")
    public void send(Message message){
        chatService.send2User(message.getToId().toString(),message);
    }
}
