package com.test.websocket.auth.api.exception;

/**
 * Created by timur on 03.05.16.
 */
public abstract class AbstractAppException extends RuntimeException{

    public AbstractAppException(String message) {
        super(message);
    }

    public AbstractAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getErrorCode();
}
