package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

import mswitalski.exercises.basickafkamongo.kafkaloader.domain.CustomerModel;

import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * Interface defining behavior for data receivers that ask datasource for all records
 * and return them as stream.
 */
public interface DataReceiver {

    void connect() throws ReceiverException;

    Stream<CustomerModel> getAllRecords() throws ReceiverException;

    void disconnect() throws ReceiverException;
}
