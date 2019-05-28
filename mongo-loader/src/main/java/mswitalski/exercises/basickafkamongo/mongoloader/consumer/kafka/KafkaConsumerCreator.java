package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Objects;
import java.util.Properties;

public class KafkaConsumerCreator<K, V> {

    Consumer<K, V> create(Properties properties) {
        return new KafkaConsumer<>(Objects.requireNonNull(properties));
    }
}
