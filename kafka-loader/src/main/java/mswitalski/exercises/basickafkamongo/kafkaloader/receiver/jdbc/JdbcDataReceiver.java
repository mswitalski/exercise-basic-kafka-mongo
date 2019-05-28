package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
public class JdbcDataReceiver implements DataReceiver {

    private String dbUrl;
    private final Properties properties;
    private DriverManagerWrapper driverManager;
    private Connection conn;

    public JdbcDataReceiver(String databaseUrl, Properties properties) {
        this.dbUrl = Objects.requireNonNull(databaseUrl);
        this.properties = Objects.requireNonNull(properties);
        this.driverManager = DriverManagerWrapper.INSTANCE;
    }

    public JdbcDataReceiver(String databaseUrl, Properties properties, DriverManagerWrapper driverManager) {
        this.dbUrl = Objects.requireNonNull(databaseUrl);
        this.properties = Objects.requireNonNull(properties);
        this.driverManager = Objects.requireNonNull(driverManager);
    }

    public void connect() throws ReceiverException {
        try {
            if (properties.isEmpty()) {
                conn = driverManager.getConnection(dbUrl);
            } else {
                conn = driverManager.getConnection(dbUrl, properties);
            }
            conn.setAutoCommit(false);
            log.info("Connected");

        } catch (SQLException e) {
            throw new ReceiverException("Could not connect properly to the datasource", e);
        }
    }

    public void disconnect() throws ReceiverException {
        try {
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            conn.close();
            log.info("Disconnected");

        } catch (SQLException e) {
            throw new ReceiverException("Could not properly disconnect from the datasource", e);
        }
    }

    public Stream<CustomerModel> getAllRecords() throws ReceiverException {
        val query = "SELECT * FROM customers";
        List<CustomerModel> results = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                results.add(convertRowToModel(rs));
            }

        } catch (SQLException e) {
            throw new ReceiverException("Could not get records from the datasource", e);
        }

        return results.stream();
    }

    private CustomerModel convertRowToModel(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String gender = rs.getString("gender");
        String occupation = rs.getString("occupation");

        return CustomerModel.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .address(address)
                .gender(gender)
                .occupation(occupation)
                .build();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }
}
