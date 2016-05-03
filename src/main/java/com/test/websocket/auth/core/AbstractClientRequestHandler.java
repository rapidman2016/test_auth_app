package com.test.websocket.auth.core;

import com.test.websocket.auth.api.exception.AppException;

/**
 * Created by timur on 03.05.16.
 */
public abstract class AbstractClientRequestHandler{
    public abstract void handle(RequestContext requestContext) throws AppException;
}
