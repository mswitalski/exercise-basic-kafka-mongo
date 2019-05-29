package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Properties;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaDataProducerTest {

    @Mock
    private KafkaProducer<Long, CustomerModel> mockedProducer;

    @Mock
    private KafkaProducerProvider<Long, CustomerModel> mockedProvider;

    private KafkaDataProducer<Long, CustomerModel> sut;
    private String topicName = "TestTopicName";

    @BeforeEach
    void setUpMocks() {
        Properties mockedProperties = mock(Properties.class);
        when(mockedProperties.getProperty("topic.name")).thenReturn(topicName);
        when(mockedProvider.provide(any(Properties.class))).thenReturn(mockedProducer);
        sut = new KafkaDataProducer<>(mockedProperties, mockedProvider);
    }

    @Test
    void shouldCloseProducerAfterFinishingTask() {
        // given
        Stream<CustomerModel> emptyStream = Stream.empty();

        // when
        sut.send(emptyStream);

        // then
        verify(mockedProducer, times(1)).close();
    }

    @Test
    void shouldSendOneCustomerWhenProvidedWithStreamWithOneElement() {
        // given
        CustomerModel desiredModel = CustomerModel.builder()
                .id(1)
                .name("TestName")
                .surname("TestSurname")
                .email("T")
                .address("TestAddress")
                .gender("TestGender")
                .occupation("TestOccupation")
                .build();
        Stream<CustomerModel> inputStream = Stream.of(desiredModel);
        ProducerRecord<Long, CustomerModel> desiredProducerRecord = new ProducerRecord<>(topicName, desiredModel);

        // when
        sut.send(inputStream);

        // then
        verify(mockedProducer, times(1)).send(eq(desiredProducerRecord), any());
    }

    @Test
    void shouldSendTwoCustomersWhenProvidedWithStreamWithTwoElements() {
        // given
        CustomerModel firstDesiredModel = CustomerModel.builder()
                .id(1)
                .name("TestName1")
                .surname("TestSurname1")
                .email("T")
                .address("TestAddress1")
                .gender("TestGender1")
                .occupation("TestOccupation1")
                .build();
        CustomerModel otherDesiredModel = CustomerModel.builder()
                .id(2)
                .name("TestName2")
                .surname("TestSurname2")
                .email("T")
                .address("TestAddress2")
                .gender("TestGender2")
                .occupation("TestOccupation2")
                .build();
        Stream<CustomerModel> inputStream = Stream.of(firstDesiredModel, otherDesiredModel);
        ProducerRecord<Long, CustomerModel> firstDesiredProducerRecord = new ProducerRecord<>(
                topicName,
                firstDesiredModel
        );
        ProducerRecord<Long, CustomerModel> otherDesiredProducerRecord = new ProducerRecord<>(
                topicName,
                firstDesiredModel
        );

        // when
        sut.send(inputStream);

        // then
        verify(mockedProducer, times(1)).send(eq(firstDesiredProducerRecord), any());
        verify(mockedProducer, times(1)).send(eq(otherDesiredProducerRecord), any());
    }
}