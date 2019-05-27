package mswitalski.exercises.basickafkamongo.kafkaloader.producer;

import java.util.stream.Stream;

public interface DataProducer <T> {

    void send(Stream<T> dataStream);
}
