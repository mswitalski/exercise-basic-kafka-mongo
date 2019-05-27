package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

import java.sql.SQLException;

public interface DatabaseReceiver {

  void connect() throws SQLException;
  void getAll();
  void disconnect() throws SQLException;
}
