package com.test.websocket.auth.api;

/**
 * Created by timur on 02.05.16.
 */
public interface IAuthService {
    ApiToken authenticate(String accessToken);
}
