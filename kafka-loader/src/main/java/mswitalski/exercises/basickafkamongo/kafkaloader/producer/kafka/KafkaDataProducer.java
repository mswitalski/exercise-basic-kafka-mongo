package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
public class KafkaDataProducer<K, V> implements DataProducer<V> {

    private Properties properties;
    private KafkaProducerCreator<K, V> producerCreator;
    private String topicName;

    public KafkaDataProducer(Properties properties, KafkaProducerCreator<K, V> producerCreator) {
        this.properties = Objects.requireNonNull(properties);
        this.producerCreator = producerCreator;
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    @Override
    public void send(Stream<V> dataStream) {
        try (KafkaProducer<K, V> conn = producerCreator.create(properties)) {
            dataStream.forEach(el -> conn.send(
                new ProducerRecord<>(topicName, el),
                (metadata, exception) -> log.info("Data was sent to Kafka to topic " + metadata.topic()))
            );
        }
    }
}
