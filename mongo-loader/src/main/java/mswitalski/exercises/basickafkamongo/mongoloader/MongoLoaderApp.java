package mswitalski.exercises.basickafkamongo.mongoloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka.KafkaConsumerCreator;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka.KafkaCustomerDataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.MongoCustomerPersister;

/**
 * Application responsible for receiving data from chosen message broker
 * and persisting it in chosen database.
 *
 * At the moment the following integrations are available:
 * - Message brokers:
 *   * Kafka
 *
 * - Databases:
 *   * MongoDB
 */
@Slf4j
public class MongoLoaderApp {

    public static void main(String... args) {
        FlowOrchestrator<CustomerModel> orchestrator = new FlowOrchestrator<>(getCustomerConsumer(), getCustomerPersister());
        orchestrator.run();
    }

    private static DataConsumer<CustomerModel> getCustomerConsumer() {
        val properties = new PropertyReader().getPropertiesByFilename("kafka-consumer.properties");
        return new KafkaCustomerDataConsumer(properties, getKafkaConsumerCreatorForCustomer());
    }

    private static DataPersister<CustomerModel> getCustomerPersister() {
        val properties = new PropertyReader().getPropertiesByFilename("mongo.properties");
        return new MongoCustomerPersister(properties);
    }

    private static KafkaConsumerCreator<Long, CustomerModel> getKafkaConsumerCreatorForCustomer() {
        return new KafkaConsumerCreator<>();
    }
}
