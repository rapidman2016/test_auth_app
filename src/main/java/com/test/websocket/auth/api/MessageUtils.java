package com.test.websocket.auth.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.test.websocket.auth.api.util.JsonSerialyzer;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by timur on 02.05.16.
 */
public class MessageUtils {
    public static ProtocolMessage getAuthErrorResponse(HttpServletRequest request, MessageSource messageSource, String error) {
        ProtocolMessage resp = new ProtocolMessage();
        return resp;
    }

    public static ProtocolMessage readFromJson(String json) throws JsonParseException {
        return JsonSerialyzer.readJsonValue(json, ProtocolMessage.class);
    }

    public static String getAccessToken(ProtocolMessage req) {
        return req.getData().get(ProtocolConstants.API_TOKEN_PARAM);
    }

    public static String createLoginRequest(String login, String password){
        ProtocolMessage message = new ProtocolMessage();
        message.setSequenceId(UUID.randomUUID().toString());
        message.setType(MessageType.LOGIN_CUSTOMER);
        Map<String,String> data = new HashMap<>();
        data.put(ProtocolConstants.LOGIN_PARAM, login);
        data.put(ProtocolConstants.PASSWORD_PARAM, password);
        message.setData(data);
        return JsonSerialyzer.toJson(message);
    }
}
