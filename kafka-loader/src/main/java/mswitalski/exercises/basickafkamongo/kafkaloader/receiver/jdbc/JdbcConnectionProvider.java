package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class JdbcConnectionProvider {

    private final String databaseUrl;
    private final Properties properties;

    public JdbcConnectionProvider(String databaseUrl, Properties properties) {
        this.databaseUrl = Objects.requireNonNull(databaseUrl);
        this.properties = Objects.requireNonNull(properties);
    }

    Connection provide() throws SQLException {
        Connection conn;
        if (properties.isEmpty()) {
            conn = DriverManager.getConnection(databaseUrl);
        } else {
            conn = DriverManager.getConnection(databaseUrl, properties);
        }

        return conn;
    }
}
