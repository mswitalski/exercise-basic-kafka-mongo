package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class KafkaCustomerDataConsumer implements DataConsumer<CustomerModel> {

    private Properties consumerProperties;
    private KafkaConsumer<Long, CustomerModel> consumer;
    private String topicName;

    public KafkaCustomerDataConsumer(Properties properties) {
        Objects.requireNonNull(properties);
        this.consumerProperties = properties;
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    @Override
    public void connect() {
        if (consumer == null) {
            consumer = new KafkaConsumer<>(consumerProperties);
            consumer.subscribe(Collections.singletonList(topicName));
            log.warn("Kafka consumer connected to the cluster");

        } else {
            log.warn("Kafka consumer is already connected");
        }
    }

    @Override
    public Stream<CustomerModel> poll() {
        if (consumer == null) {
            log.error("Kafka consumer is not connected to the cluster");
        }
        ConsumerRecords<Long, CustomerModel> consumerRecords = consumer.poll(Duration.ofSeconds(1));
        Spliterator<ConsumerRecord<Long, CustomerModel>> spliterator = consumerRecords.spliterator();

        return StreamSupport.stream(spliterator, false)
                .map(ConsumerRecord::value);
    }

    @Override
    public void disconnect() {
        consumer.unsubscribe();
        consumer.close();
        consumer = null;
    }
}
