package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

public class KafkaDataProducer<T> implements DataProducer<T> {

    private KafkaProducer<Long, T> producer;
    private String topicName;

    public KafkaDataProducer(Properties properties) {
        Objects.requireNonNull(properties);
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
        this.producer = new KafkaProducer<>(properties);
    }

    @Override
    public void send(Stream<T> dataStream) {
        dataStream.forEach(el -> producer.send(new ProducerRecord<>(topicName, el)));
        producer.close();
    }
}
