package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

public class KafkaDataProducer<T> implements DataProducer<T> {

    private KafkaProducer<Long, T> producer;
    private Properties properties;

    public KafkaDataProducer(Properties properties) {
        Objects.requireNonNull(properties);
        this.properties = properties;
        this.producer = new KafkaProducer<>(this.properties);
    }

    @Override
    public void send(Stream<T> dataStream) {
        dataStream.forEach(element ->
                producer.send(new ProducerRecord<>(properties.getProperty("topic.name"), element))
        );
    }
}
