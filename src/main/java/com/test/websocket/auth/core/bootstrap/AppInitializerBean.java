package com.test.websocket.auth.core.bootstrap;

import com.test.websocket.auth.api.dao.ICustomerDao;
import com.test.websocket.auth.api.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by timur on 03.05.16.
 */
@Component
@DependsOn(value = "mongoConfig")
public class AppInitializerBean {
    @Autowired
    private ICustomerDao customerDao;

    @PostConstruct
    public void init(){
        if(customerDao.getDocumentCountByCriteria(new ArrayList<Criteria>()) == 0){
            Customer customer = new Customer();
            customer.setEmail("customer@email.com");
            customer.setPassword("customer");
            customerDao.save(customer);
        }
    }
}
