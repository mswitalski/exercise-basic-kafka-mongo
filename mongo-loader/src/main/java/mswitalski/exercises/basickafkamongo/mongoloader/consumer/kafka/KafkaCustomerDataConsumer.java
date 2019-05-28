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

    private Properties consumerProperties;
    private KafkaConsumerCreator<Long, CustomerModel> consumerCreator;
    private String topicName;

    public KafkaCustomerDataConsumer(Properties properties, KafkaConsumerCreator<Long, CustomerModel> consumerCreator) {
        this.consumerProperties = Objects.requireNonNull(properties);
        this.consumerCreator = consumerCreator;
        this.topicName = Objects.requireNonNull(properties.getProperty("topic.name"));
    }

    @Override
    public Stream<CustomerModel> poll() {
        try (Consumer<Long, CustomerModel> consumer = consumerCreator.create(consumerProperties)) {
            consumer.subscribe(Collections.singletonList(topicName));
            ConsumerRecords<Long, CustomerModel> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            Spliterator<ConsumerRecord<Long, CustomerModel>> spliterator = consumerRecords.spliterator();

            return StreamSupport.stream(spliterator, false)
                .map(ConsumerRecord::value);
        }
    }
}
