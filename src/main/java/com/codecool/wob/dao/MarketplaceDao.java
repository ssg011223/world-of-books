package com.codecool.wob.dao;

import com.codecool.wob.model.Marketplace;
import com.codecool.wob.util.JdbcConnection;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarketplaceDao implements Dao<Marketplace>{
    @Override
    public void save(Iterable<Marketplace> marketplaces) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "INSERT INTO marketplace (id, marketplace_name)" +
                "VALUES (?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET marketplace_name = excluded.marketplace_name";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Marketplace marketplace: marketplaces) {
                ps.setInt(1, marketplace.getId());
                ps.setString(2, marketplace.getName());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
