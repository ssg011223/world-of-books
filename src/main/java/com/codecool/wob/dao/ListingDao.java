package com.codecool.wob.dao;

import com.codecool.wob.model.Listing;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ListingDao extends Dao<Listing>{
    void deleteBefore(LocalDateTime localDateTime);
}
