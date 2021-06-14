package com.codecool.wob.util;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public class JdbcConnection {
    private static String jdbcUrl;
    private static String username;
    private static String password;

    public static void init(String jdbcUrl, String username, String password) {
        JdbcConnection.jdbcUrl = jdbcUrl;
        JdbcConnection.username = username;
        JdbcConnection.password = password;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
