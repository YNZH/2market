package com.gjf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: GJF
 * @Date : 2018/05/15
 * Time   : 18:07
 */
@Slf4j
@Configuration
public class WebSocketListener {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public SessionConnectListener sessionConnectListener(){
        return new SessionConnectListener();
    }
    @Bean
    public SessionConnectedListener sessionConnectedListener(){
        return new SessionConnectedListener();
    }

    @Bean
    public SessionDisconnectListener sessionDisconnectListener(){
        return new SessionDisconnectListener();
    }


    public class SessionConnectListener implements ApplicationListener<SessionConnectEvent>{
        @Override
        public void onApplicationEvent(SessionConnectEvent event) {
            log.info(event.getMessage().toString());
            log.info("收到命令 SessionConnect");
        }
    }

    public class SessionConnectedListener implements ApplicationListener<SessionConnectedEvent>{
        @Override
        public void onApplicationEvent(SessionConnectedEvent event) {
            log.info(event.getMessage().toString());
            log.info("收到命令 SessionConnected");

            //设置 sessionId:userId;  userId;sessionId
            setUserOnline(StompHeaderAccessor.wrap(event.getMessage()));
        }
    }

    public class SessionDisconnectListener implements ApplicationListener<SessionDisconnectEvent>{
        @Override
        public void onApplicationEvent(SessionDisconnectEvent event) {
            log.info(event.getMessage().toString());
            log.info("收到命令 SessionDisconnect");
            setUserOffline(StompHeaderAccessor.wrap(event.getMessage()));
        }
    }

    private void setUserOnline(StompHeaderAccessor stompHeaders){
        String oldSessionId = stringRedisTemplate.opsForValue().get(Objects.requireNonNull(getClientId(stompHeaders)));
        if (oldSessionId!=null){
            stringRedisTemplate.delete(oldSessionId);
        }
        stringRedisTemplate.opsForValue().set(Objects.requireNonNull(getClientId(stompHeaders)),stompHeaders.getSessionId());
        stringRedisTemplate.opsForValue().set(stompHeaders.getSessionId(),Objects.requireNonNull(getClientId(stompHeaders)));
    }

    private void setUserOffline(StompHeaderAccessor stompHeaderAccessor){
        String sessionId = stompHeaderAccessor.getSessionId();
        stringRedisTemplate.delete(stringRedisTemplate.opsForValue().get(sessionId));
        stringRedisTemplate.delete(sessionId);
    }

    private String getClientId(StompHeaderAccessor stompHeaders) {
        GenericMessage message = (GenericMessage) stompHeaders.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        Object nativeMap = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
        log.info(Objects.requireNonNull(nativeMap).toString());
        if ( nativeMap instanceof Map){
            return (String) ((LinkedList)(((Map)(nativeMap)).get("clientId"))).getFirst();
        }
        return null;
    }
}
