package com.test.websocket.auth.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.test.websocket.auth.api.util.JsonSerialyzer;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by timur on 02.05.16.
 */
public class MessageUtils {
    public static ProtocolMessage createAuthErrorResponse(HttpServletRequest request, MessageSource messageSource, String error) {
        ProtocolMessage resp = new ProtocolMessage();
        return resp;
    }

    public static String createLoginRequest(String login, String password){
        ProtocolMessage message = createProtocolMessage(MessageType.LOGIN_CUSTOMER, UUID.randomUUID().toString());
        Map<String,String> data = new HashMap<>();
        data.put(ProtocolConstants.EMAIL_PARAM, login);
        data.put(ProtocolConstants.PASSWORD_PARAM, password);
        message.setData(data);
        return JsonSerialyzer.toJson(message);
    }

    public static ProtocolMessage createLoginResponse(ApiToken apiToken, ProtocolMessage request) {
        ProtocolMessage message = createProtocolMessage(MessageType.CUSTOMER_API_TOKEN, request.getSequenceId());
        Map<String,String> data = new HashMap<>();
        data.put(ProtocolConstants.API_TOKEN_PARAM, apiToken.getToken());
        SimpleDateFormat sdf = new SimpleDateFormat(ProtocolConstants.DATE_TIME_FORMAT);
        data.put(ProtocolConstants.API_TOKEN_EXPIRATION_DATE_PARAM, sdf.format(apiToken.getExpirationDate()));
        message.setData(data);
        return message;
    }

    public static ProtocolMessage readFromJson(String json) throws JsonParseException {
        return JsonSerialyzer.readJsonValue(json, ProtocolMessage.class);
    }

    public static String getAccessToken(ProtocolMessage req) {
        return req.getData().get(ProtocolConstants.API_TOKEN_PARAM);
    }

    public static String getEmail(ProtocolMessage protocolMessage) {
        return protocolMessage.getData().get(ProtocolConstants.EMAIL_PARAM);
    }

    public static String getPassword(ProtocolMessage protocolMessage) {
        return protocolMessage.getData().get(ProtocolConstants.PASSWORD_PARAM);
    }

    private static ProtocolMessage createProtocolMessage(MessageType messageType, String sequenceId) {
        ProtocolMessage message = new ProtocolMessage();
        message.setSequenceId(sequenceId);
        message.setType(messageType);
        return message;
    }

}
