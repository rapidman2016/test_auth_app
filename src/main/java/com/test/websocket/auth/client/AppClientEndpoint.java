package com.test.websocket.auth.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

/**
 * Created by timur on 03.05.16.
 */
public class AppClientEndpoint extends Endpoint {
    private static final Logger log = LoggerFactory.getLogger(AppClientEndpoint.class);
    private MessageHandler.Whole<String> messageHandler;

    public AppClientEndpoint(MessageHandler.Whole<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        log.info("on open");
        session.addMessageHandler(String.class, messageHandler);
    }
}
