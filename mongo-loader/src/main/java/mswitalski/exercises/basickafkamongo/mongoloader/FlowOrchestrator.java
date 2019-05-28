package mswitalski.exercises.basickafkamongo.mongoloader;

import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;

import java.util.stream.IntStream;

class FlowOrchestrator<T> {

    private DataConsumer<T> dataConsumer;
    private DataPersister<T> dataPersister;

    FlowOrchestrator(DataConsumer<T> dataConsumer, DataPersister<T> dataPersister) {
        this.dataConsumer = dataConsumer;
        this.dataPersister = dataPersister;
    }

    void run() {
        /*
            TODO: This is a temporary solution and should be improved.
         */
        IntStream.range(0, 100).forEach(ignored -> dataConsumer.poll()
            .forEach(dataPersister::persistOne)
        );
    }
}
