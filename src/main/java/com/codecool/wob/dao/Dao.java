package com.codecool.wob.dao;

public interface Dao<T> {
    void save(Iterable<T> ts);
}
