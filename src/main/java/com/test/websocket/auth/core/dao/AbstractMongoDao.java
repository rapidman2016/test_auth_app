package com.test.websocket.auth.core.dao;

import com.mongodb.WriteResult;
import com.test.websocket.auth.api.dao.IAbstractMongoDao;
import com.test.websocket.auth.api.exception.CustomerNotFoundException;
import com.test.websocket.auth.api.exception.StaleObjectException;
import com.test.websocket.auth.api.model.AbstractMongoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Abstract mongo DAO
 */
public abstract class AbstractMongoDao<T extends AbstractMongoEntity> implements IAbstractMongoDao<T> {
    protected Class<T> entityClass;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @SuppressWarnings("unchecked")
    public AbstractMongoDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        if (entity.getId() != null) {
            throw new RuntimeException("Can't save already saved " + entity);
        }
        entity.setUpdateDate(new Date());
        mongoTemplate.save(entity);
        return getById(entity.getId());
    }

    @Override
    public T update(String id, Update update){
        Query query = Query.query(Criteria.where("id").is(id));
        update.inc("version", Integer.valueOf(1));
        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public T update(T entity){
       return update(entity, true);
    }

    @Override
    public T update(T entity, boolean optimisticLocking) {
        Objects.requireNonNull(entity.getId(), "Entity ID must not be null");
        Criteria criteria = Criteria.where("id").is(entity.getId());
        if(optimisticLocking){
            criteria.andOperator(Criteria.where("version").is(entity.getVersion()));
        }
        Query query = Query.query(criteria);
        Update update = entity.getUpdate();
        T result = mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), entityClass);
        if (result == null) {
            T existsEnt = getById(entity.getId());
            if (existsEnt == null) {
                throw new CustomerNotFoundException("Can't find and update entity " + entityClass + " by id " + entity.getId());
            }else{
                throw new StaleObjectException("Can't find and modify entity " + entityClass + " by id " + entity.getId() + " and version " + entity.getVersion());
            }
        }
        return result;
    }

    @Override
    public T getById(String id) {
        return mongoTemplate.findById(id, entityClass);
    }

    @Override
    public void dropCollection() {
        mongoTemplate.remove(new Query(), entityClass);
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public void remove(String id) {
        mongoTemplate.remove(getById(id));
    }

    public T findOneByProperties(Criteria... criteria) {
        Query query = getQuery(criteria);
        return mongoTemplate.findOne(query, entityClass);
    }

    protected Query getQuery(Criteria[] criteria) {
        Query query = new Query();
        for (Criteria criterion : criteria) {
            query.addCriteria(criterion);
        }
        return query;
    }

    protected Query getQuery(List<Criteria> criteriaList) {
        Query query = new Query();
        for (Criteria criteria : criteriaList) {
            query.addCriteria(criteria);
        }
        return query;
    }

    public List<T> findListByProperties(Criteria... criteria) {
        Query query = getQuery(criteria);
        return mongoTemplate.find(query, entityClass);
    }

    public List<T> findListByProperties(List<Criteria> criteriaList) {
        Query query = getQuery(criteriaList);
        return mongoTemplate.find(query, entityClass);
    }


    public List<T> findListByProperties(int page, int size, String sortProperty, String sortDirection, List<Criteria> criteriaList) {
        Sort sort = null;
        if (StringUtils.isEmpty(sortProperty)) {
            if (sortProperty.equals("id")) // sort by id doesn't work without that
                sortProperty = "_id";
            Sort.Direction direction =
                    Sort.Direction.DESC.name().equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = new Sort(direction, sortProperty);
        }

        Query query = getQuery(page, size, sort, criteriaList);
        List<T> list = mongoTemplate.find(query, entityClass);
        return list;
    }

    public long getDocumentCountByCriteria(List<Criteria> criteriaList) {
        Query query = getQuery(criteriaList);
        return mongoTemplate.count(query, entityClass);
    }

    protected Query getQuery(int page, int size, Sort sort, List<Criteria> criteriaList) {
        Query query = new Query();
        for (Criteria criterion : criteriaList) {
            query.addCriteria(criterion);
        }
        Pageable pageable;
        if (sort != null) {
            query.with(sort);
            pageable = new PageRequest(page, size, sort);
        } else {
            pageable = new PageRequest(page, size);
        }
        query.with(pageable);
        return query;
    }

    protected WriteResult updateByCriteria(Update update, List<Criteria> criteriaList) {
        Query query = new Query();
        for (Criteria criterion : criteriaList) {
            query.addCriteria(criterion);
        }
        return mongoTemplate.updateMulti(query, update, entityClass);
    }

    protected WriteResult removeByCriteria(List<Criteria> criteriaList) {
        Query query = new Query();
        for (Criteria criterion : criteriaList) {
            query.addCriteria(criterion);
        }
        return mongoTemplate.remove(query, entityClass);
    }

    @Override
    public WriteResult renameFieldName(String oldValue, String newValue){
        return mongoTemplate.updateMulti(new Query(), new Update().rename(oldValue, newValue), entityClass);
    }

}
