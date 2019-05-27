package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.kafkaloader.domain.CustomerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Slf4j
public class PostgresReceiver implements DatabaseReceiver {

  private String dbUrl;
  private final Properties properties;
  private Connection conn;

  public PostgresReceiver() {
    dbUrl = "jdbc:postgresql://localhost:5432/kafka-mongo-db";
    properties = new Properties();
    properties.setProperty("user", "postgres");
    properties.setProperty("password", "postgres");
    properties.setProperty("ssl", "false");
  }

  public void connect() throws SQLException {
    log.info("Trying to connect...");
    conn = DriverManager.getConnection(dbUrl, properties);
  }

  public void getAll() {
    log.info("Trying to get all...");
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String gender = rs.getString("gender");
        String occupation = rs.getString("occupation");
        CustomerModel customer = CustomerModel.builder()
            .id(id)
            .name(name)
            .surname(surname)
            .email(email)
            .address(address)
            .gender(gender)
            .occupation(occupation)
            .build();
        log.info(customer.toString());
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() throws SQLException {
    log.info("Trying to disconnect...");
    conn.close();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    conn.close();
  }
}
