package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Objects;
import java.util.Properties;

public class KafkaConsumerProvider<K, V> {

    Consumer<K, V> provide(Properties properties) {
        return new KafkaConsumer<>(Objects.requireNonNull(properties));
    }
}
