package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

import mswitalski.exercises.basickafkamongo.kafkaloader.domain.CustomerModel;

import java.util.stream.Stream;

/**
 * Interface defining behavior for data receivers that ask datasource for all records
 * and return them as stream.
 *
 * <b>Usage contract</b>: it is mandatory to {@link #connect()} first to the datasource before
 * calling {@link #getAllRecords()}. After work is done and the connection is not needed
 * anymore remember to {@link #disconnect()}.
 */
public interface DataReceiver {

    void connect() throws ReceiverException;

    Stream<CustomerModel> getAllRecords() throws ReceiverException;

    void disconnect() throws ReceiverException;
}
