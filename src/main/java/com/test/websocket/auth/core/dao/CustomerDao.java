package com.test.websocket.auth.core.dao;

import com.test.websocket.auth.api.dao.ICustomerDao;
import com.test.websocket.auth.api.model.Customer;
import org.springframework.stereotype.Repository;

/**
 * Created by timur on 03.05.16.
 */
@Repository
public class CustomerDao extends AbstractMongoDao<Customer> implements ICustomerDao{
}
