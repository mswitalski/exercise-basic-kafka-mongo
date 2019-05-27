package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class DriverManagerWrapper {
    static DriverManagerWrapper INSTANCE = new DriverManagerWrapper();

    private DriverManagerWrapper() {
    }

    Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    Connection getConnection(String url, Properties properties) throws SQLException {
        return DriverManager.getConnection(url, properties);
    }
}
