package mswitalski.exercises.basickafkamongo.kafkaloader.producer.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Serializes any Java object into JSON string for Kafka.
 */
@Slf4j
public class KafkaJsonSerializer implements Serializer {

    @Override
    public void configure(Map ignored1, boolean ignored2) {
    }

    /**
     * Serializes passed object into bytes array representing JSON string.
     *
     * @param ignored ignored parameter
     * @param o       object to be serialized
     * @return serialized object to bytes array representing JSON string
     */
    @Override
    public byte[] serialize(String ignored, Object o) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsBytes(o);

        } catch (Exception e) {
            log.error("Cloud not serialize given object", e);

            return new byte[0];
        }
    }

    @Override
    public void close() {
    }
}
