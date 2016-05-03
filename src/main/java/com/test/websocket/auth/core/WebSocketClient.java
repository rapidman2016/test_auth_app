package com.test.websocket.auth.core;

import com.test.websocket.auth.client.AppClientEndpoint;
import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;

/**
 * Created by timur on 03.05.16.
 */
@Component
public class WebSocketClient {
    private static final Logger log = LoggerFactory.getLogger(WebSocketClient.class);
    public static final String SERVER_ENDPOINT_URL_TMPL = "ws://%s:%s/%s/app";
    @Value("${app.host}")
    private String host;

    @Value("${app.context_path}")
    private String contextPath;

    @Value("${app.port}")
    private Integer serverPort;

    private Session session;
    private MessageHandler.Whole<String> messageHandler;

    public void setMessageHandler(MessageHandler.Whole<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void send(String message){
        if(!isConnected()){
            connect();
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            String err = "Can't send";
            log.error(err, e);
            throw new RuntimeException(err, e);
        }
    }

    public void connect(){
        final ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();
        ClientEndpointConfig clientConfig = configBuilder.build();
        ClientManager client = ClientManager.createClient();
        try {
            session = client.connectToServer(new AppClientEndpoint(messageHandler), clientConfig,
                    new URI(String.format(SERVER_ENDPOINT_URL_TMPL, host, serverPort, contextPath)));
        } catch (Exception e) {
            String err = "Can't connect";
            log.error(err, e);
            throw new RuntimeException(err, e);
        }
    }

    @PreDestroy
    public void disconnect(){
        try {
            if(isConnected()) session.close();
            session = null;
        } catch (IOException ignore) {}
    }

    private boolean isConnected() {
        return session != null && session.isOpen();
    }
}
