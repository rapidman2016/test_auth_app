package com.test.websocket.auth.api.exception;

import com.test.websocket.auth.api.ProtocolConstants;

/**
 * Created by timur on 03.05.16.
 */
public class StaleObjectException extends AbstractAppException {

    public StaleObjectException(String message) {
        super(message);
    }

    public StaleObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return ProtocolConstants.ENTITY_CONCURRENT_MODIFICATION;
    }
}
