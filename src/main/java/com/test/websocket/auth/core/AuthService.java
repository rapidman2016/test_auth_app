package com.test.websocket.auth.core;

import com.test.websocket.auth.api.ApiToken;
import com.test.websocket.auth.api.IAuthService;
import com.test.websocket.auth.api.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by timur on 03.05.16.
 */
@Service
public class AuthService implements IAuthService {

    @Override
    public ApiToken authenticate(String accessToken) throws AppException {
        return new ApiToken("test token", new Date(), "111");
    }

    @Override
    public ApiToken login(String email, String password) throws AppException {
        return new ApiToken("test token", new Date(), "111");
    }
}
