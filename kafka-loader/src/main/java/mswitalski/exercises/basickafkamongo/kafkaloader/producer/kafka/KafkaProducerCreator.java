package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Objects;
import java.util.Properties;

public class KafkaProducerCreator<K, V> {

    Producer<K, V> create(Properties properties) {
        return new KafkaProducer<>(Objects.requireNonNull(properties));
    }
}
