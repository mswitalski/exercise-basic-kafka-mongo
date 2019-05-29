package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.common.domain.validator.CustomerModelNullValidator;
import mswitalski.exercises.basickafkamongo.common.domain.validator.ModelValidator;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka.KafkaDataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka.KafkaProducerProvider;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.ReceiverException;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc.CustomerRepository;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.jdbc.JdbcConnectionProvider;

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

    public static void main(String... args) {
        DataLoader<CustomerModel> dataLoader = new DataLoader<>(
            getCustomerDataReceiver(getJdbcConnector()),
            getCustomerNullValidator(),
            getCustomerDataProducer(getKafkaProducerProviderForCustomer())
        );
        dataLoader.loadData();
    }

    private static ModelValidator<CustomerModel> getCustomerNullValidator() {
        return new CustomerModelNullValidator();
    }

    private static DataReceiver<CustomerModel> getCustomerDataReceiver(JdbcConnectionProvider connector) {
        return new CustomerRepository(connector);
    }

    private static JdbcConnectionProvider getJdbcConnector() {
        val properties = new PropertyReader().getPropertiesByFilename("postgres.properties");

        return new JdbcConnectionProvider((String) properties.get("url"), properties);
    }

    private static DataProducer<CustomerModel> getCustomerDataProducer(KafkaProducerProvider<Long, CustomerModel> provider) {
        val properties = new PropertyReader().getPropertiesByFilename("kafka-producer.properties");

        return new KafkaDataProducer<>(properties, provider);
    }

    private static KafkaProducerProvider<Long, CustomerModel> getKafkaProducerProviderForCustomer() {
        return new KafkaProducerProvider<>();
    }
}
