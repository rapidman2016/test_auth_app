package com.test.websocket.auth.api;

import java.util.Date;

/**
 * Created by timur on 02.05.16.
 */
public class ApiToken {
    private String token;
    private Date expirationDate;
    private String customerId;

    public ApiToken(String token, Date expirationDate, String customerId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCustomerId() {
        return customerId;
    }

}
