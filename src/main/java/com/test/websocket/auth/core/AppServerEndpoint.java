package com.test.websocket.auth.core;

import com.test.websocket.auth.api.IAuthService;
import com.test.websocket.auth.api.MessageType;
import com.test.websocket.auth.api.MessageUtils;
import com.test.websocket.auth.api.ProtocolMessage;
import com.test.websocket.auth.api.exception.*;
import com.test.websocket.auth.api.util.JsonSerialyzer;
import com.test.websocket.auth.core.handler.AbstractClientRequestHandler;
import com.test.websocket.auth.core.handler.LoginHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by timur on 02.05.16.
 */
public class AppServerEndpoint extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(AppServerEndpoint.class);
    private static Map<MessageType, AbstractClientRequestHandler> handlers = new HashMap<>();
    static {
        handlers.put(MessageType.LOGIN_CUSTOMER, new LoginHandler());
    }

    @Autowired
    private IAuthService authService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("connection established");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("connection closed");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String json = message.getPayload();
        log.info("in:{}", json);
        ProtocolMessage req = null;
        try {
            req = JsonSerialyzer.readJsonValue(json, ProtocolMessage.class);
            AbstractClientRequestHandler handler = handlers.get(req.getType());
            if(handler == null){
                String err = "Can't find handler for request " + req;
                log.error(err);
                throw new IllegalStateException("Unrecognized message type " + req.getType());
            }
            handler.handle(new RequestContext(authService, session, message, req));
        } catch (AbstractAppException e) {
            log.error("Error while handling request", e);
            ProtocolMessage errResp = MessageUtils.createAuthErrorResponse(req, e);
            session.sendMessage(new TextMessage(JsonSerialyzer.toJson(errResp)));
        }catch (Throwable t){
            ProtocolMessage errResp = MessageUtils.createFatalErrorResponse(req);
            session.sendMessage(new TextMessage(JsonSerialyzer.toJson(errResp)));
        }
    }
}
