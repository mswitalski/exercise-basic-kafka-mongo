package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class KafkaCustomerDataConsumer implements DataConsumer<CustomerModel> {

    private final Properties properties;
    private final String topicName;
    private final KafkaConsumerProvider<Long, CustomerModel> consumerProvider;

    public KafkaCustomerDataConsumer(Properties properties, KafkaConsumerProvider<Long, CustomerModel> consumerProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
        this.consumerProvider = Objects.requireNonNull(consumerProvider);
    }

    @Override
    public Stream<CustomerModel> poll() {
        try (Consumer<Long, CustomerModel> consumer = consumerProvider.provide(properties)) {
            consumer.subscribe(Collections.singletonList(topicName));
            ConsumerRecords<Long, CustomerModel> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            Spliterator<ConsumerRecord<Long, CustomerModel>> spliterator = consumerRecords.spliterator();

            return StreamSupport.stream(spliterator, false)
                .map(ConsumerRecord::value);
        }
    }
}
