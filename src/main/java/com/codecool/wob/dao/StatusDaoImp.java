package com.codecool.wob.dao;

import com.codecool.wob.model.Status;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class StatusDaoImp implements StatusDao {
    @Override
    public void save(Iterable<Status> statuses) {
        String sql = "INSERT INTO status (id, status_name)" +
                "VALUES (?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET status_name = excluded.status_name";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            for (Status status: statuses) {
                ps.setInt(1, status.getId());
                ps.setString(2, status.getName());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Collection<Status> findAll() {
        return null;
    }

    @Override
    public boolean isExisting(Integer id) {
        String sql = "SELECT id FROM status WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            boolean res = rs.next();

            rs.close();

            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
