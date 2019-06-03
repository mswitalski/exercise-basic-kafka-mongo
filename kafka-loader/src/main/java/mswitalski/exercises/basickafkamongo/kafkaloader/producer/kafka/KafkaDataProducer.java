package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Provides producer for sending data to a Kafka message broker.
 *
 * @param <K> class of Kafka's message key
 * @param <V> class of a model to be sent
 */
@Slf4j
public class KafkaDataProducer<K, V> implements DataProducer<V> {

    private final Properties properties;
    private final KafkaProducerProvider<K, V> producerProvider;
    private final String topicName;

    /**
     * Constructs KafkaDataProducer with properties for Kafka and Kafka producer provider.
     *
     * @param properties       properties for Kafka producer
     * @param producerProvider KafkaProducer provider
     */
    public KafkaDataProducer(Properties properties, KafkaProducerProvider<K, V> producerProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.producerProvider = Objects.requireNonNull(producerProvider);
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    /**
     * Sends data stream into a Kafka message broker on a topic that was provided
     * in properties passed to class constructor. For each call of this method,
     * a new connection to broker is created and after sending all the data,
     * connection is closed.
     *
     * @param dataStream data stream to be loaded into the Kafka message broker
     */
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
