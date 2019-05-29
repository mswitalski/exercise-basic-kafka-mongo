package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

import java.util.stream.Stream;

/**
 * Interface defining behavior for data receivers that ask datasource for all records
 * and return them as stream.
 */
public interface DataReceiver<T> {

    Stream<T> getAll();
}
