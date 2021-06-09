package com.codecool.wob.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    public static String getDBUrl() throws IOException {
        return getProperties().getProperty("db.url");
    }

    public static String getDBUsername() throws IOException {
        return getProperties().getProperty("db.username");
    }

    public static String getDBPassword() throws IOException {
        return getProperties().getProperty("db.password");
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "app.properties"));
        return properties;
    }
}
