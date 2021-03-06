package com.codecool.wob.dao;

import com.codecool.wob.model.Marketplace;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MarketplaceDaoImp implements MarketplaceDao{
    @Override
    public void save(Iterable<Marketplace> marketplaces) {
        String sql = "INSERT INTO marketplace (id, marketplace_name)" +
                "VALUES (?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET marketplace_name = excluded.marketplace_name";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

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

    @Override
    public Collection<Marketplace> findAll() {
        String sql = "SELECT * FROM marketplace";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Marketplace> marketplaces = new ArrayList<>();

            while (rs.next()) {
                Marketplace marketplace = new Marketplace();
                marketplace.setId(rs.getInt("id"));
                marketplace.setName(rs.getString("marketplace_name"));
                marketplaces.add(marketplace);
            }
            return marketplaces;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
}
