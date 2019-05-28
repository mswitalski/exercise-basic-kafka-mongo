package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaProducerCreator<K, V> {

    KafkaProducer<K, V> create(Properties properties) {
        return new KafkaProducer<>(properties);
    }
}
