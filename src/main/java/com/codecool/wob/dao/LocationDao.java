package com.codecool.wob.dao;

import com.codecool.wob.model.Location;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class LocationDao implements Dao<Location>{
    @Override
    public void save(Iterable<Location> locations) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "INSERT INTO location (id, manager_name, phone, address_primary, address_secondary, country, town, postal_code)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET manager_name = excluded.manager_name," +
                "phone = excluded.phone," +
                "address_primary = excluded.address_primary," +
                "address_secondary = excluded.address_secondary," +
                "country = excluded.country," +
                "town = excluded.town," +
                "postal_code = excluded.postal_code";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Location location: locations) {
                ps.setObject(1, location.getId());
                ps.setString(2, location.getManagerName());
                ps.setString(3, location.getPhone());
                ps.setString(4, location.getPrimaryAddress());
                ps.setString(5, location.getSecondaryAddress());
                ps.setString(6, location.getCountry());
                ps.setString(7, location.getTown());
                ps.setString(8, location.getPostalCode());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Collection<Location> findAll() {
        return null;
    }
}
