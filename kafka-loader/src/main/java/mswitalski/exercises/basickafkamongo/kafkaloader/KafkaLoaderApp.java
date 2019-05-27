package mswitalski.exercises.basickafkamongo.kafkaloader;

import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DatabaseReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.PostgresReceiver;
import org.apache.log4j.BasicConfigurator;

import java.sql.SQLException;

/**
 * Application responsible for receiving data from chosen database
 * and loading it into chosen message broker.
 *
 * At the moment the following integrations are available:
 *   - Message brokers:
 *     * none
 *
 *   - Databases:
 *     * none
 */
public class KafkaLoaderApp {

  public static void main(String... args) throws SQLException {
    BasicConfigurator.configure();

    System.out.println("Hello world from Kafka Loader App!");
    DatabaseReceiver receiver = new PostgresReceiver();
    receiver.connect();
    receiver.getAll();
    receiver.disconnect();
  }
}
