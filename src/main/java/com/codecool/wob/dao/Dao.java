package com.codecool.wob.dao;

import java.util.Collection;

public interface Dao<T> {
    void save(Iterable<T> ts);
    Collection<T> findAll();
}
