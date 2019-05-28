package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
public class KafkaDataProducer<T> implements DataProducer<T> {

    private Properties properties;
    private String topicName;

    public KafkaDataProducer(Properties properties) {
        this.properties = Objects.requireNonNull(properties);
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    @Override
    public void send(Stream<T> dataStream) {
        try (KafkaProducer<Long, T> conn = new KafkaProducer<>(properties)) {
            dataStream.forEach(el -> conn.send(
                new ProducerRecord<>(topicName, el),
                (metadata, exception) -> log.info("Data was sent to Kafka to topic " + metadata.topic()))
            );
        }
    }
}
