package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Objects;
import java.util.Properties;

/**
 * Provides Kafka producer that can be used to interact with Kafka message broker.
 *
 * @param <K> class of Kafka's message key
 * @param <V> class of a model to be sent
 */
public class KafkaProducerProvider<K, V> {

    /**
     * Provides Kafka producer configured with properties passed as an method argument.
     *
     * @param properties properties for Kafka producer
     * @return configured Kafka producer
     */
    Producer<K, V> provide(Properties properties) {
        return new KafkaProducer<>(Objects.requireNonNull(properties));
    }
}
