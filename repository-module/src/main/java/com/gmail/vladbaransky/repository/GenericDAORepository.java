package com.gmail.vladbaransky.repository;

import java.util.List;

public interface GenericDAORepository<I, T> {
    List<T> getObjectByPage(int startPosition, int objectByPage);

    T addObject(T object);

    T getObjectById(I id);

    T updateObject(T object);
}
