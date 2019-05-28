package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class JdbcConnector {

    private String dbUrl;
    private final Properties properties;
    private DriverManagerWrapper driverManager;

    public JdbcConnector(String databaseUrl, Properties properties) {
        this.dbUrl = Objects.requireNonNull(databaseUrl);
        this.properties = Objects.requireNonNull(properties);
        this.driverManager = DriverManagerWrapper.INSTANCE;
    }

    JdbcConnector(String databaseUrl, Properties properties, DriverManagerWrapper driverManager) {
        this.dbUrl = Objects.requireNonNull(databaseUrl);
        this.properties = Objects.requireNonNull(properties);
        this.driverManager = Objects.requireNonNull(driverManager);
    }

    Connection getConnection() throws SQLException {
        Connection conn;
        if (properties.isEmpty()) {
            conn = driverManager.getConnection(dbUrl);
        } else {
            conn = driverManager.getConnection(dbUrl, properties);
        }
        conn.setAutoCommit(false);

        return conn;
    }
}
