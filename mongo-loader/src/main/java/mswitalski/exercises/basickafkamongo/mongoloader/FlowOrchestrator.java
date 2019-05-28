package mswitalski.exercises.basickafkamongo.mongoloader;

import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;

class FlowOrchestrator<T> {

    private DataConsumer<T> dataConsumer;
    private DataPersister<T> dataPersister;

    FlowOrchestrator(DataConsumer<T> dataConsumer, DataPersister<T> dataPersister) {
        this.dataConsumer = dataConsumer;
        this.dataPersister = dataPersister;
    }

    void run() {
        dataPersister.connect();
        dataConsumer.connect();

        for (int i = 0; i < 100; i++) {
            dataConsumer.poll()
                .forEach(dataPersister::persist);
        }

        dataPersister.disconnect();
        dataConsumer.disconnect();
    }
}
