package com.test.websocket.auth.core;

import com.test.websocket.auth.api.ApiToken;
import com.test.websocket.auth.api.IAuthService;
import com.test.websocket.auth.api.dao.IApiTokenDao;
import com.test.websocket.auth.api.dao.ICustomerDao;
import com.test.websocket.auth.api.exception.AuthenticationIsFailed;
import com.test.websocket.auth.api.exception.CustomerNotFoundException;
import com.test.websocket.auth.api.exception.CustomerNotAuthorizedException;
import com.test.websocket.auth.api.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by timur on 03.05.16.
 */
@Service
public class AuthService implements IAuthService {
    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IApiTokenDao apiTokenDao;


    @Override
    public ApiToken authenticate(String accessToken) {
        Customer customer = customerDao.findOneByProperties(Criteria.where("apiToken.id").is(accessToken));
        if(customer == null){
            throw new CustomerNotAuthorizedException("User is not authorized");
        }
        return customer.getApiToken();
    }

    @Override
    public ApiToken login(String email, String password){
        Objects.requireNonNull(email, "Email must not be null");
        Customer customer = customerDao.findOneByProperties(Criteria.where("email").is(email));
        if(customer == null){
            throw new CustomerNotFoundException("Can't find customer by email '" + email + "'");
        }
        if(!customer.getPassword().equals(password)){
            throw new AuthenticationIsFailed("Password is not valid");
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        ApiToken apiToken = new ApiToken(cal.getTime(), email);
        apiToken = apiTokenDao.save(apiToken);
        customer.setApiToken(apiToken);
        customerDao.update(customer, false);
        return apiToken;
    }
}
