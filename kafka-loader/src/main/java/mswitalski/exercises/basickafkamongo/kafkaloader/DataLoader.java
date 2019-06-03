package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.validator.ModelValidator;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Provides basic data loading functionality by reading data with provided receiver,
 * validating it and loading it into a chosen message broker with provided producer.
 *
 * @param <T> class of a model to be processed
 */
@Slf4j
class DataLoader<T> {

    private final DataReceiver<T> receiver;
    private final ModelValidator<T> validator;
    private final DataProducer<T> producer;

    DataLoader(DataReceiver<T> receiver, ModelValidator<T> validator, DataProducer<T> producer) {
        this.receiver = Objects.requireNonNull(receiver);
        this.validator = Objects.requireNonNull(validator);
        this.producer = Objects.requireNonNull(producer);
    }

    void loadData() {
        Stream<T> allRecords = receiver.getAll();
        Stream<T> filteredRecords = allRecords.flatMap(o -> {
            if (validator.isValid(o)) {
                return Stream.of(o);
            } else {
                log.warn("Rejected invalid object: " + o);

                return Stream.empty();
            }
        });
        producer.send(filteredRecords);
    }
}
