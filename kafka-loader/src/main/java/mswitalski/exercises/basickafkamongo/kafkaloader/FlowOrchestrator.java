package mswitalski.exercises.basickafkamongo.kafkaloader;

import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.validator.ModelValidator;
import mswitalski.exercises.basickafkamongo.kafkaloader.producer.DataProducer;
import mswitalski.exercises.basickafkamongo.kafkaloader.receiver.DataReceiver;

import java.util.stream.Stream;

@Slf4j
class FlowOrchestrator<T> {

    private DataReceiver<T> receiver;
    private ModelValidator<T> validator;
    private DataProducer<T> producer;

    FlowOrchestrator(DataReceiver<T> receiver, ModelValidator<T> validator, DataProducer<T> producer) {
        this.receiver = receiver;
        this.validator = validator;
        this.producer = producer;
    }

    void run() {
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