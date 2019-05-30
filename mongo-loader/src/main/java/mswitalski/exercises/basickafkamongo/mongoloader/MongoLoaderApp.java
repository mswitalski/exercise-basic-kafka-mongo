package mswitalski.exercises.basickafkamongo.mongoloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka.KafkaConsumerProvider;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka.KafkaDataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.mongo.MongoClientProvider;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.mongo.MongoCustomerPersister;

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
        DataLoader<CustomerModel> dataLoader = new DataLoader<>(
            getCustomerConsumer(getKafkaConsumerProviderForCustomer()),
            getCustomerPersister(getMongoClientProvider())
        );
        dataLoader.loadData();
    }

    private static DataConsumer<CustomerModel> getCustomerConsumer(KafkaConsumerProvider<Long, CustomerModel> provider) {
        val properties = new PropertyReader().getPropertiesByFilename("kafka-consumer.properties");

        return new KafkaDataConsumer<>(properties, provider);
    }

    private static DataPersister<CustomerModel> getCustomerPersister(MongoClientProvider provider) {
        val properties = new PropertyReader().getPropertiesByFilename("mongo.properties");

        return new MongoCustomerPersister(properties, provider);
    }

    private static MongoClientProvider getMongoClientProvider() {
        return new MongoClientProvider();
    }

    private static KafkaConsumerProvider<Long, CustomerModel> getKafkaConsumerProviderForCustomer() {
        return new KafkaConsumerProvider<>();
    }
}
