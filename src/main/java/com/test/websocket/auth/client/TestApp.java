package com.test.websocket.auth.client;

import com.test.websocket.auth.client.AppClientEndpoint;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Session;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by timur on 02.05.16.
 */
public class TestApp {
    public static void main(String[] args) {
        try {

            final ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();
            configBuilder.configurator(new ClientEndpointConfig.Configurator() {
                @Override
                public void beforeRequest(final Map<String, List<String>> headers) {
                    headers.put("Origin", Arrays.asList("http://localhost"));
                }
            });
            ClientEndpointConfig clientConfig = configBuilder.build();
            ClientManager client = ClientManager.createClient();
            Session sess = client.connectToServer(AppClientEndpoint.class, clientConfig, new URI("ws://localhost:8080/stockticker/app"));
            sess.getBasicRemote().sendText("ping");
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
    }
}
