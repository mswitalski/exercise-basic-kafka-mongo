package mswitalski.exercises.basickafkamongo.mongoloader.persister.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import mswitalski.exercises.basickafkamongo.mongoloader.persister.DataPersister;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Objects;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class MongoCustomerPersister implements DataPersister<CustomerModel> {

    private final Properties properties;
    private final MongoClientProvider clientProvider;

    public MongoCustomerPersister(Properties properties, MongoClientProvider clientProvider) {
        this.properties = Objects.requireNonNull(properties);
        this.clientProvider = Objects.requireNonNull(clientProvider);
    }

    @Override
    public void persistOne(CustomerModel customer) {
        try (MongoClient client = clientProvider.provide(properties)) {
            getCollection(client).insertOne(customer);
        }
    }

    private MongoCollection<CustomerModel> getCollection(MongoClient client) {
        MongoDatabase database = client.getDatabase(properties.getProperty("database"));

        return database.getCollection(properties.getProperty("collection.customer"), CustomerModel.class);
    }
}
