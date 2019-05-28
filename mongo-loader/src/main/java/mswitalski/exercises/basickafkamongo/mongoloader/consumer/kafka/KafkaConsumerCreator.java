package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class KafkaConsumerCreator<K, V> {

    KafkaConsumer<K, V> create(Properties properties) {
        return new KafkaConsumer<>(properties);
    }
}
