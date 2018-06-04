package com.gjf.controller;

import com.gjf.mapper.MessageMapper;
import com.gjf.model.Message;
import com.gjf.model.ResultBean;
import com.gjf.service.ChatService;
import com.gjf.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RelationSupport;
import javax.sound.midi.MetaMessage;
import java.util.List;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 10:38
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/checkOffline/{userId}",method = RequestMethod.GET)
    public ResultBean offlineMsgList(@PathVariable Long userId){
        List<Message> msgs = messageService.getOfflineMsg(userId);
        if (!msgs.isEmpty()){
            for (Message msg : msgs
                 ) {
                simpMessagingTemplate.convertAndSendToUser(msg.getToId().toString(),"/message",msg);
            }
            return ResultBean.ok(msgs);
        }
      return ResultBean.ok();
    }

    @RequestMapping(value = "/history/{userId}",method = RequestMethod.GET)
    public ResultBean messageHistory(@PathVariable Long userId){
      return ResultBean.ok(messageService.getHistoryMsg(userId));
    }

}
