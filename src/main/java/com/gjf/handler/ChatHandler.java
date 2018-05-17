package com.gjf.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: GJF
 * @Date : 2018/05/14
 * Time   : 13:46
 */
@Slf4j
@Service
public class ChatHandler extends DefaultHandshakeHandler {
    private static final Map<String,String> ONLINE_USERS;

    static {
        ONLINE_USERS = new ConcurrentHashMap<>();
    }

    public ChatHandler() {
        super();
    }

    public ChatHandler(RequestUpgradeStrategy requestUpgradeStrategy) {
        super(requestUpgradeStrategy);
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("detesflksnfsn++++++++++++++++++++++++++++++++++");
        log.info(request.toString());
        return super.determineUser(request, wsHandler, attributes);
    }
}
