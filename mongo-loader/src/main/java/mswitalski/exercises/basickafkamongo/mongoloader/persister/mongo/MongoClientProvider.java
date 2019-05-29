package mswitalski.exercises.basickafkamongo.mongoloader.persister.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoClientProvider {

    private final CodecRegistry pojoCodecRegistry;

    public MongoClientProvider() {
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    MongoClient provide(Properties properties) {
        ServerAddress address = new ServerAddress(
            properties.getProperty("host"),
            Integer.parseInt(properties.getProperty("port"))
        );

        return new MongoClient(
            address,
            prepareMongoCredentials(properties),
            MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build()
        );
    }

    private MongoCredential prepareMongoCredentials(Properties properties) {
        String user = properties.getProperty("user");
        String authDb = properties.getProperty("auth_db");
        char[] password = properties.getProperty("password").toCharArray();

        return MongoCredential.createCredential(user, authDb, password);
    }
}
