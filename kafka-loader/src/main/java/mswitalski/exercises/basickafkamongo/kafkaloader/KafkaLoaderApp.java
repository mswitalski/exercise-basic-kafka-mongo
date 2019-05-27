package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.kafkaloader.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.kafkaloader.domain.validator.CustomerModelNullValidator;
import mswitalski.exercises.basickafkamongo.kafkaloader.domain.validator.ModelValidator;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka.KafkaDataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc.JdbcDataReceiver;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import org.apache.log4j.BasicConfigurator;

import java.util.stream.Stream;

/**
 * Application responsible for receiving data from chosen database
 * and loading it into chosen message broker.
 *
 * At the moment the following integrations are available:
 *   - Message brokers:
 *     * Kafka
 *
 *   - Databases:
 *     * any SQL database supported by JDBC
 */
@Slf4j
public class KafkaLoaderApp {

    public static void main(String... args) throws ReceiverException {
        BasicConfigurator.configure();

        System.out.println("Hello world from Kafka Loader App!");
        Stream<CustomerModel> allRecords = runReceiver();
        ModelValidator<CustomerModel> validator = new CustomerModelNullValidator();
        Stream<CustomerModel> filteredRecords = allRecords
                .flatMap(customer -> {
                    if (validator.isValid(customer)) {
                        return Stream.of(customer);
                    } else {
                        log.warn("Rejected invalid customer: " + customer);
                        return Stream.empty();
                    }
                });
        val properties = new PropertyReader().getPropertiesByFilename("kafka.properties");
        DataProducer<CustomerModel> producer = new KafkaDataProducer<>(properties);
        producer.send(filteredRecords);
    }

    private static Stream<CustomerModel> runReceiver() throws ReceiverException {
        val properties = new PropertyReader().getPropertiesByFilename("postgres.properties");
        DataReceiver receiver = new JdbcDataReceiver((String) properties.get("url"), properties);
        receiver.connect();
        Stream<CustomerModel> allRecords = receiver.getAllRecords();
        receiver.disconnect();

        return allRecords;
    }
}
