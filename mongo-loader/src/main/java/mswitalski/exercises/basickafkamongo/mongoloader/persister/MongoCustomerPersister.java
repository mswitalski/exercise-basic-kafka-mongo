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

import java.util.Arrays;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class MongoCustomerPersister implements DataPersister<CustomerModel> {

    private Properties properties;
    private MongoClient mongoClient;
    private CodecRegistry pojoCodecRegistry;
    private MongoCollection<CustomerModel> collection;

    public MongoCustomerPersister(Properties properties) {
        this.properties = properties;
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    @Override
    public void connect() {
        if (mongoClient == null) {
            String user = properties.getProperty("user");
            String authDb = properties.getProperty("auth_db");
            char[] password = properties.getProperty("password").toCharArray();
            MongoCredential credential = MongoCredential.createCredential(user, authDb, password);
            ServerAddress address = new ServerAddress(
                    properties.getProperty("host"),
                    Integer.parseInt(properties.getProperty("port"))
            );
            mongoClient = new MongoClient(
                    address,
                    credential,
                    MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build()
            );
            MongoDatabase database = mongoClient.getDatabase(properties.getProperty("database"));
            collection = database.getCollection(properties.getProperty("collection"), CustomerModel.class);

        } else {
            log.warn("Connection to MongoDB is already open");
        }
    }

    @Override
    public void persist(CustomerModel customer) {
        if (mongoClient == null) {
            log.warn("Connection to MongoDB is closed");
            throw new RuntimeException("Tried to persist while no connection");
        }
        collection.insertOne(customer);
    }

    @Override
    public void disconnect() {
        if (mongoClient == null) {
            log.warn("Connection to MongoDB is already closed");
            return;
        }
        mongoClient.close();
        mongoClient = null;
        collection = null;
    }
}
