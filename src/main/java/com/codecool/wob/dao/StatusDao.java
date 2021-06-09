package com.codecool.wob.dao;

import com.codecool.wob.model.Status;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatusDao implements Dao<Status> {
    @Override
    public void save(Iterable<Status> statuses) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "INSERT INTO status (id, status_name)" +
                "VALUES (?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET status_name = excluded.status_name";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

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
}
