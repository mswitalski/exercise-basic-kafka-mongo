package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.common.domain.validator.CustomerModelNullValidator;
import mswitalski.exercises.basickafkamongo.common.domain.validator.ModelValidator;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka.KafkaDataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka.KafkaProducerCreator;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc.CustomerRepository;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc.JdbcConnector;

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
        FlowOrchestrator<CustomerModel> orchestrator = new FlowOrchestrator<>(
            getCustomerDataReceiver(getJdbcConnector()),
            getCustomerNullValidator(),
            getCustomerDataProducer()
        );

        orchestrator.run();
    }

    private static ModelValidator<CustomerModel> getCustomerNullValidator() {
        return new CustomerModelNullValidator();
    }

    private static DataReceiver<CustomerModel> getCustomerDataReceiver(JdbcConnector connector) {
        return new CustomerRepository(connector);
    }

    private static JdbcConnector getJdbcConnector() {
        val properties = new PropertyReader().getPropertiesByFilename("postgres.properties");

        return new JdbcConnector((String) properties.get("url"), properties);
    }

    private static DataProducer<CustomerModel> getCustomerDataProducer() {
        val properties = new PropertyReader().getPropertiesByFilename("kafka-producer.properties");
        return new KafkaDataProducer<>(properties, getKafkaProducerCreatorForCustomer());
    }

    private static KafkaProducerCreator<Long, CustomerModel> getKafkaProducerCreatorForCustomer() {
        return new KafkaProducerCreator<>();
    }
}
