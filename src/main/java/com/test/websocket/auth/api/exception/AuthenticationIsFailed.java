package com.test.websocket.auth.api.exception;

import com.test.websocket.auth.api.ProtocolConstants;

/**
 * Created by timur on 03.05.16.
 */
public class AuthenticationIsFailed extends AbstractAppException {

    public AuthenticationIsFailed(String message) {
        super(message);
    }

    public AuthenticationIsFailed(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return ProtocolConstants.CUSTOMER_AUTH_FAILED;
    }
}
