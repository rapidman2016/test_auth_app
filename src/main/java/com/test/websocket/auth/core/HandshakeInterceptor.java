package com.test.websocket.auth.core;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by timur on 03.05.16.
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String,Object> attributes) throws Exception{
        System.out.println("Before Handshake!");
        return super.beforeHandshake(request,response,wsHandler,attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,WebSocketHandler wsHandler,
                               Exception ex){
        System.out.println("After HandShake!");
        super.afterHandshake(request,response,wsHandler,ex);
    }
}
