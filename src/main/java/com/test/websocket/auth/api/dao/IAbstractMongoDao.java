package com.test.websocket.auth.api.dao;

import com.mongodb.WriteResult;
import com.test.websocket.auth.api.model.AbstractMongoEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by timur on 18.04.14.
 */
public interface IAbstractMongoDao<T extends AbstractMongoEntity> {
    T save(T entity);

    T update(String id, Update update);

    T update(T entity);

    T getById(String id);

    void dropCollection();

    List<T> findAll();

    void remove(String id);

    List<T> findListByProperties(int page, int size, String sortProperty, String sortDirection, List<Criteria> criteriaList);

    List<T> findListByProperties(List<Criteria> criteriaList);

    T findOneByProperties(Criteria... criteria);

    long getDocumentCountByCriteria(List<Criteria> criteriaList);

    WriteResult renameFieldName(String oldValue, String newValue);

    T update(T entity, boolean optimisticLocking);
}
