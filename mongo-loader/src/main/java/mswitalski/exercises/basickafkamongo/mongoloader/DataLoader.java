package mswitalski.exercises.basickafkamongo.mongoloader;

import mswitalski.exercises.basickafkamongo.mongoloader.consumer.DataConsumer;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;

import java.util.Objects;

/**
 * Class responsible for supervising data loading process from consuming it from
 * given message broker to persisting it to chosen database.
 *
 * @param <T> class of a model that will be processed
 */
class DataLoader<T> {

    private final DataConsumer<T> dataConsumer;
    private final DataPersister<T> dataPersister;

    DataLoader(DataConsumer<T> dataConsumer, DataPersister<T> dataPersister) {
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
        this.dataPersister = Objects.requireNonNull(dataPersister);
    }

    void loadData() {
        while (true) {
            dataConsumer.poll().forEach(dataPersister::persistOne);
        }
    }
}
