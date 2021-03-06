package mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CustomerRepository implements DataReceiver<CustomerModel> {

    private final JdbcConnectionProvider connectionProvider;

    public CustomerRepository(JdbcConnectionProvider connectionProvider) {
        this.connectionProvider = Objects.requireNonNull(connectionProvider);
    }

    @Override
    public Stream<CustomerModel> getAll() {
        String query = "SELECT * FROM customers";
        List<CustomerModel> results = new ArrayList<>();

        try (Connection conn = connectionProvider.provide();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                results.add(mapRowToModel(rs));
            }

        } catch (SQLException e) {
            throw new ReceiverException("Could not successfully interact with SQL databse", e);
        }

        return results.stream();
    }

    private CustomerModel mapRowToModel(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String gender = rs.getString("gender");
        String occupation = rs.getString("occupation");

        return CustomerModel.builder()
            .name(name)
            .surname(surname)
            .email(email)
            .address(address)
            .gender(gender)
            .occupation(occupation)
            .build();
    }
}
