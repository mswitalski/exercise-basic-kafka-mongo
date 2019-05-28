package mswitalski.exercises.basickafkamongo.mongoloader.persister;

public interface DataPersister<T> {

    void connect();

    void persist(T object);

    void disconnect();
}
