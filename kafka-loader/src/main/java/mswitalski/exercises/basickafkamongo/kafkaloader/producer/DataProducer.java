package mswitalski.exercises.basickafkamongo.kafkaloader.producer;

import java.util.stream.Stream;

/**
 * Provides interface for data producers that load data into any data storage like message brokers
 * or databases.
 *
 * @param <T> class of a model to be loaded
 */
public interface DataProducer<T> {

    /**
     * Load data stream into the data storage without waiting for any acknowledges.
     *
     * @param dataStream data stream to be loaded into the data storage
     */
    void send(Stream<T> dataStream);
}
