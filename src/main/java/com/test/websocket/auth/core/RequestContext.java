package com.test.websocket.auth.core;

import com.test.websocket.auth.api.IAuthService;
import com.test.websocket.auth.api.ProtocolMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by timur on 03.05.16.
 */
public class RequestContext {
    private IAuthService authService;
    private final WebSocketSession session;
    private final TextMessage message;
    private final ProtocolMessage protocolMessage;

    public RequestContext(IAuthService authService,
                          WebSocketSession session,
                          TextMessage message,
                          ProtocolMessage protocolMessage) {
        this.authService = authService;
        this.session = session;
        this.message = message;
        this.protocolMessage = protocolMessage;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public TextMessage getMessage() {
        return message;
    }

    public ProtocolMessage getProtocolMessage() {
        return protocolMessage;
    }

    public IAuthService getAuthService() {
        return authService;
    }
}
