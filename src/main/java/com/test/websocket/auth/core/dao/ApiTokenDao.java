package com.test.websocket.auth.core.dao;

import com.test.websocket.auth.api.ApiToken;
import com.test.websocket.auth.api.dao.IApiTokenDao;
import org.springframework.stereotype.Repository;

/**
 * Created by timur on 03.05.16.
 */
@Repository
public class ApiTokenDao extends AbstractMongoDao<ApiToken> implements IApiTokenDao {
}
