package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.JdbcDataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

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
@Slf4j
public class KafkaLoaderApp {

    public static void main(String... args) throws ReceiverException {
        BasicConfigurator.configure();

        System.out.println("Hello world from Kafka Loader App!");
        runReceiver();
    }

    private static void runReceiver() throws ReceiverException {
        val connectionUrl = "jdbc:postgresql://localhost:5432/kafka-mongo-db";
        val properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "postgres");
        properties.setProperty("ssl", "false");
        DataReceiver receiver = new JdbcDataReceiver(connectionUrl, properties);
        receiver.connect();
        receiver.getAllRecords().forEach(c -> log.info(c.toString()));
        receiver.disconnect();
    }
}
