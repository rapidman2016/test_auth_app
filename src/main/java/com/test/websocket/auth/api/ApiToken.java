package com.test.websocket.auth.api;

import com.test.websocket.auth.api.model.AbstractMongoEntity;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

/**
 * Created by timur on 02.05.16.
 */
@org.springframework.data.mongodb.core.mapping.Document(collection = "api_token")
public class ApiToken extends AbstractMongoEntity{
    private Date expirationDate;
    private String email;

    public ApiToken() {
    }

    public ApiToken(Date expirationDate, String email) {
        this.expirationDate = expirationDate;
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected void exposeFieldsToSave(Update update) {
        update.set("expirationDate", expirationDate);
        update.set("customerId", email);
    }

    @Override
    public String toString() {
        return "{" +
                "expirationDate=" + expirationDate +
                ", email='" + email + '\'' +
                '}';
    }

}
