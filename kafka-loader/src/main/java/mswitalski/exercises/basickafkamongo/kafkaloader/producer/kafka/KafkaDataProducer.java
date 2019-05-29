package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
public class KafkaDataProducer<K, V> implements DataProducer<V> {

    private final Properties properties;
    private final KafkaProducerProvider<K, V> producerProvider;
    private final String topicName;

    public KafkaDataProducer(Properties properties, KafkaProducerProvider<K, V> producerProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.producerProvider = Objects.requireNonNull(producerProvider);
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    @Override
    public void send(Stream<V> dataStream) {
        try (Producer<K, V> conn = producerProvider.provide(properties)) {
            dataStream.forEach(value -> conn.send(
                new ProducerRecord<>(topicName, value),
                (metadata, exception) -> log.info(value + " was sent to Kafka to topic: " + metadata.topic()))
            );
        }
    }
}
