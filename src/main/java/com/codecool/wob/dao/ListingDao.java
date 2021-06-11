package com.codecool.wob.dao;

import com.codecool.wob.model.Listing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ListingDao extends Dao<Listing, UUID>{
    void deleteBefore(LocalDateTime localDateTime);
}
