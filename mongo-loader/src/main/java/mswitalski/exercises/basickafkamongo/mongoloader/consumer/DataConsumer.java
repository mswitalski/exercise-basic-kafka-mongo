package mswitalski.exercises.basickafkamongo.mongoloader.consumer;

import java.util.stream.Stream;

public interface DataConsumer<T> {

    Stream<T> poll();
}
