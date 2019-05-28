package mswitalski.exercises.basickafkamongo.mongoloader.consumer.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mswitalski.exercises.basickafkamongo.common.domain.CustomerModel;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class KafkaCustomerDeserializer implements Deserializer<CustomerModel> {

    @Override
    public void configure(Map<String, ?> ignored1, boolean ignored2) {
    }

    @Override
    public CustomerModel deserialize(String ignored, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        CustomerModel deserializedCustomer = null;

        try {
            deserializedCustomer = mapper.readValue(data, CustomerModel.class);

        } catch (Exception e) {
            log.error("Could not deserialize customer data", e);
        }

        return deserializedCustomer;
    }

    @Override
    public void close() {
    }
}
