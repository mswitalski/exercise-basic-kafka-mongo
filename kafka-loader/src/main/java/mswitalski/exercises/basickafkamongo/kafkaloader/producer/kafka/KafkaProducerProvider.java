package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Objects;
import java.util.Properties;

public class KafkaProducerProvider<K, V> {

    Producer<K, V> provide(Properties properties) {
        return new KafkaProducer<>(Objects.requireNonNull(properties));
    }
}
