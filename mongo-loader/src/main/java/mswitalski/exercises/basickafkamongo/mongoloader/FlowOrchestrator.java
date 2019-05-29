package mswitalski.exercises.basickafkamongo.mongoloader;

import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;

import java.util.Objects;
import java.util.stream.IntStream;

class FlowOrchestrator<T> {

    private final DataConsumer<T> dataConsumer;
    private final DataPersister<T> dataPersister;

    FlowOrchestrator(DataConsumer<T> dataConsumer, DataPersister<T> dataPersister) {
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
        this.dataPersister = Objects.requireNonNull(dataPersister);
    }

    void run() {
        while (true) {
            dataConsumer.poll().forEach(dataPersister::persistOne);
        }
    }
}
