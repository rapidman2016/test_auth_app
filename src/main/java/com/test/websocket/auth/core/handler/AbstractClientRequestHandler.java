package com.test.websocket.auth.core.handler;

import com.test.websocket.auth.api.exception.AbstractAppException;
import com.test.websocket.auth.core.RequestContext;

/**
 * Created by timur on 03.05.16.
 */
public abstract class AbstractClientRequestHandler{
    public abstract void handle(RequestContext requestContext) throws AbstractAppException;
}
