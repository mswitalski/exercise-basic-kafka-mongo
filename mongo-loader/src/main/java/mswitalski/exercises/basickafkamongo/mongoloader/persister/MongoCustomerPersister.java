package mswitalski.exercises.basickafkamongo.mongoloader.persister;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Objects;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class MongoCustomerPersister implements DataPersister<CustomerModel> {

    private final Properties properties;
    private final CodecRegistry pojoCodecRegistry;

    public MongoCustomerPersister(Properties properties) {
        this.properties = Objects.requireNonNull(properties);
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    @Override
    public void persistOne(CustomerModel customer) {
        try (MongoClient client = getMongoClient()) {
            getCollection(client).insertOne(customer);
        }
    }

    private MongoClient getMongoClient() {
        ServerAddress address = new ServerAddress(
            properties.getProperty("host"),
            Integer.parseInt(properties.getProperty("port"))
        );

        return new MongoClient(
            address,
            prepareMongoCredentials(),
            MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build()
        );
    }

    private MongoCredential prepareMongoCredentials() {
        String user = properties.getProperty("user");
        String authDb = properties.getProperty("auth_db");
        char[] password = properties.getProperty("password").toCharArray();

        return MongoCredential.createCredential(user, authDb, password);
    }

    private MongoCollection<CustomerModel> getCollection(MongoClient client) {
        MongoDatabase database = client.getDatabase(properties.getProperty("database"));

        return database.getCollection(properties.getProperty("collection"), CustomerModel.class);
    }
}
