package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaCustomerDataConsumerTest {

    @Mock
    private KafkaConsumer<Long, CustomerModel> mockedConsumer;

    @Mock
    private KafkaConsumerProvider<Long, CustomerModel> mockedProvider;

    @Mock
    private ConsumerRecords<Long, CustomerModel> modelConsumerRecords;

    private KafkaCustomerDataConsumer sut;

    @BeforeEach
    void setUpMocks() {
        String topicName = "TestTopicName";
        Properties mockedProperties = mock(Properties.class);
        when(mockedProperties.getProperty("topic.name")).thenReturn(topicName);
        when(mockedProvider.provide(any(Properties.class))).thenReturn(mockedConsumer);
        sut = new KafkaCustomerDataConsumer(mockedProperties, mockedProvider);
    }


    @Test
    void shouldCloseConsumerAfterFinishingPolling() {
        // given
        when(mockedConsumer.poll(any())).thenReturn(ConsumerRecords.empty());

        // when
        sut.poll();

        // then
        verify(mockedConsumer, times(1)).close();
    }

    @Test
    void shouldConsumeOneCustomerWhenKafkaTopicHoldsOneMessage() {
        // given
        CustomerModel desiredModel = CustomerModel.builder()
                .name("TestName")
                .surname("TestSurname")
                .email("T")
                .address("TestAddress")
                .gender("TestGender")
                .occupation("TestOccupation")
                .build();
        Spliterator<ConsumerRecord<Long, CustomerModel>> resultSpliterator =
                createConsumerRecordSpliterator(desiredModel);
        when(mockedConsumer.poll(any())).thenReturn(modelConsumerRecords);
        when(modelConsumerRecords.spliterator()).thenReturn(resultSpliterator);

        // when
        Set<CustomerModel> resultSet = sut.poll().collect(Collectors.toSet());

        // then
        assertThat(resultSet.size()).isEqualTo(1);
        assertThat(resultSet.contains(desiredModel)).isTrue();
    }

    @Test
    void shouldConsumeTwoCustomersWhenKafkaTopicHoldsTwoMessages() {
        // given
        CustomerModel firstDesiredModel = CustomerModel.builder()
                .name("TestName")
                .surname("TestSurname")
                .email("T")
                .address("TestAddress")
                .gender("TestGender")
                .occupation("TestOccupation")
                .build();
        CustomerModel otherDesiredModel = CustomerModel.builder()
                .name("TestName2")
                .surname("TestSurname2")
                .email("T")
                .address("TestAddress2")
                .gender("TestGender2")
                .occupation("TestOccupation2")
                .build();
        Spliterator<ConsumerRecord<Long, CustomerModel>> resultSpliterator =
                createConsumerRecordSpliterator(firstDesiredModel, otherDesiredModel);
        when(mockedConsumer.poll(any())).thenReturn(modelConsumerRecords);
        when(modelConsumerRecords.spliterator()).thenReturn(resultSpliterator);

        // when
        Set<CustomerModel> resultSet = sut.poll().collect(Collectors.toSet());

        // then
        assertThat(resultSet.size()).isEqualTo(2);
        assertThat(resultSet.contains(firstDesiredModel)).isTrue();
        assertThat(resultSet.contains(otherDesiredModel)).isTrue();
    }

    private Spliterator<ConsumerRecord<Long, CustomerModel>> createConsumerRecordSpliterator(CustomerModel... model) {
        return Arrays.stream(model)
                .map(m -> new ConsumerRecord<>("", 0, 0L, 0L, m))
                .spliterator();
    }
}