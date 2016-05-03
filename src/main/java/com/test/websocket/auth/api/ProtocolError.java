package com.test.websocket.auth.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.test.websocket.auth.api.ErrorType.*;

/**
 * Protocol error
 */
public enum ProtocolError {
    //generic error 0xx
    ERROR_GENERIC_ERROR(0, GENERIC_SERVER_ERROR),
    ERROR_DATABASE_ERROR(1, GENERIC_SERVER_ERROR),

    //resource not found error 1xx
    ERROR_USER_NOT_FOUND(100, RESOURCE_NOT_FOUND_ERROR),

    //security error 2xx
    ERROR_USER_NOT_AUTHORIZED(200, SECURITY_ERROR),
    ERROR_SECURITY_RESTRICTION(201, SECURITY_ERROR),
    ERROR_REQUEST_SIGNATURE_NOT_VALID(202, SECURITY_ERROR),
    ERROR_AUTHENTICATION_FAILED_CREDENTIALS_IS_NOT_VALID(203, SECURITY_ERROR);

    private static Logger log = LoggerFactory.getLogger(ProtocolError.class);

    private int code;
    private ErrorType type;

    ProtocolError(int code, ErrorType type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public ErrorType getType() {
        return type;
    }

    public String getErrorTypeName() {
        return type.name();
    }

    public static ProtocolError getBycode(int errorCode) {
        for (ProtocolError error : ProtocolError.values()) {
            if (error.code == errorCode) {
                return error;
            }
        }
        log.error("Can't recognize errorCode=" + errorCode);
        return ProtocolError.ERROR_GENERIC_ERROR;
    }
}
