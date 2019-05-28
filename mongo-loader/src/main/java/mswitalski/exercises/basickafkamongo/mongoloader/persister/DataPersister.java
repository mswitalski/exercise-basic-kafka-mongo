package mswitalski.exercises.basickafkamongo.mongoloader.persister;

public interface DataPersister<T> {

    void persistOne(T object);
}
