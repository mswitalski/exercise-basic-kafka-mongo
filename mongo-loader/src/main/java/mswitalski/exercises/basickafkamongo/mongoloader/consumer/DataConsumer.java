package mswitalski.exercises.basickafkamongo.mongoloader.consumer;

import java.util.stream.Stream;

public interface DataConsumer<T> {

    void connect();

    Stream<T> poll();

    void disconnect();
}
