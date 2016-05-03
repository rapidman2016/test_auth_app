package com.test.websocket.auth.api;

/**
 * Created by timur on 01.05.16.
 */
public final class ProtocolConstants {
    public static final String CHARSET_NAME = "UTF-8";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_TOKEN_PARAM = "api_token";
    public static final String API_TOKEN_EXPIRATION_DATE_PARAM = "api_token_expiration_date";
    public static final String EMAIL_PARAM = "email";
    public static final String PASSWORD_PARAM = "password";
    public static final String ERROR_DESC_PARAM = "error_description";
    public static final String ERROR_CODE_PARAM = "error_code";

    //error codes
    public static final String CUSTOMER_AUTH_FAILED = "customer.authFailed";
    public static final String CUSTOMER_NOT_AUTHORIZED = "customer.notAuthorized";
    public static final String CUSTOMER_NOT_FOUND = "customer.notFound";
    public static final String ENTITY_CONCURRENT_MODIFICATION = "entity.concurrentModification";
    public static final String FATAL_ERROR = "server.fatalError";
}
