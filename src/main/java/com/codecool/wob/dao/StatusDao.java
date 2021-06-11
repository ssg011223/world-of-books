package com.codecool.wob.dao;

import com.codecool.wob.model.Status;

public interface StatusDao extends Dao<Status, Integer>{
    boolean isExisting(Integer id);
}
