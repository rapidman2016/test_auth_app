package com.test.websocket.auth.api.exception;

import com.test.websocket.auth.api.ProtocolConstants;

/**
 * Created by timur on 03.05.16.
 */
public class CustomerNotAuthorizedException extends AbstractAppException {

    public CustomerNotAuthorizedException(String message) {
        super(message);
    }

    public CustomerNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return ProtocolConstants.CUSTOMER_NOT_AUTHORIZED;
    }
}
