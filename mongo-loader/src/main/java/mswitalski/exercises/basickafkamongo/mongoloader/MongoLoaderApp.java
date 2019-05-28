package mswitalski.exercises.basickafkamongo.mongoloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.common.util.PropertyReader;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka.KafkaCustomerDataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.MongoCustomerPersister;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.Objects;

/**
 * Application responsible for receiving data from chosen message broker
 * and persisting it in chosen database.
 * <p>
 * At the moment the following integrations are available:
 * - Message brokers:
 * * none
 * <p>
 * - Databases:
 * * none
 */
@Slf4j
public class MongoLoaderApp {

    public static void main(String... args) {
        System.out.println("Hello world from Mongo Loader App!");

        DOMConfigurator.configure(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource
                ("log4j.xml")).getPath());
        runCustomer();
    }

    private static void runCustomer() {
        val properties = new PropertyReader().getPropertiesByFilename("kafka-consumer.properties");
        DataConsumer<CustomerModel> consumer = new KafkaCustomerDataConsumer(properties);
        DataPersister<CustomerModel> persister = getPersister();
        persister.connect();
        consumer.connect();

        for (int i = 0; i < 100; i++) {
            consumer.poll()
                    .forEach(persister::persist);
        }

        persister.disconnect();
        consumer.disconnect();
    }

    private static DataPersister<CustomerModel> getPersister() {
        val properties = new PropertyReader().getPropertiesByFilename("mongo.properties");

        return new MongoCustomerPersister(properties);
    }
}
