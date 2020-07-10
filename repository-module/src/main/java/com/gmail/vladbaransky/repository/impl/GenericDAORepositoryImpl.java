package com.gmail.vladbaransky.repository.impl;

import com.gmail.vladbaransky.repository.GenericDAORepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDAORepositoryImpl<I, T> implements GenericDAORepository<I, T> {
    protected Class<T> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDAORepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public List<T> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName();
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }

    @Override
    public T addObject(T object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public T getObjectById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T updateObject(T object) {
        entityManager.merge(object);
        return object;
    }
}
