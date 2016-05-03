package com.test.websocket.auth.api.model;

import com.test.websocket.auth.api.ApiToken;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by timur on 03.05.16.
 */
@org.springframework.data.mongodb.core.mapping.Document(collection = "customer")
public class Customer extends AbstractMongoEntity{
    @Indexed(unique = true)
    private String email;
    private String password;
    private ApiToken apiToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApiToken getApiToken() {
        return apiToken;
    }

    public void setApiToken(ApiToken apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    protected void exposeFieldsToSave(Update update) {
        update.set("email", email);
        update.set("password", password);
        update.set("apiToken", apiToken);
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", apiToken=" + apiToken +
                '}';
    }
}
