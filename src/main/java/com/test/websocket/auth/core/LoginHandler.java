package com.test.websocket.auth.core;

import com.test.websocket.auth.api.ApiToken;
import com.test.websocket.auth.api.MessageUtils;
import com.test.websocket.auth.api.ProtocolMessage;
import com.test.websocket.auth.api.exception.AppException;
import com.test.websocket.auth.api.util.JsonSerialyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

import static com.test.websocket.auth.api.MessageUtils.*;

/**
 * Created by timur on 03.05.16.
 */
public class LoginHandler extends AbstractClientRequestHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
    @Override
    public void handle(RequestContext requestContext) throws AppException{
        ProtocolMessage request = requestContext.getProtocolMessage();
        ApiToken apiToken = requestContext.getAuthService().login(getEmail(request), getPassword(request));
        ProtocolMessage response = MessageUtils.createLoginResponse(apiToken, request);
        log.debug("token {}, response {}", apiToken, response);
        try {
            requestContext.getSession().sendMessage(new TextMessage(JsonSerialyzer.toJson(response)));
        } catch (IOException e) {
            log.error("Can't send message " + response, e);
        }
    }
}
