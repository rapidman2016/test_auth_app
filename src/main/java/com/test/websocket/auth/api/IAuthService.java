package com.test.websocket.auth.api;

import com.test.websocket.auth.api.exception.AppException;

/**
 * Created by timur on 02.05.16.
 */
public interface IAuthService {
    ApiToken authenticate(String accessToken) throws AppException;;

    ApiToken login(String email, String password) throws AppException;
}
