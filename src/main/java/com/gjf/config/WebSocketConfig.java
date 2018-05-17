package com.gjf.config;

//import com.gjf.handler.ChatHandler;
import com.gjf.interceptor.ChatInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: GJF
 * @Date : 2018/05/14
 * Time   : 13:40
 */
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer{
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatHandler(),"/api/chat/")
//                .addInterceptors(new ChatInterceptor())
//                .withSockJS();
//    }
//
//    @Bean
//    public WebSocketHandler chatHandler(){
//        return new ChatHandler();
//    }
//
//}
