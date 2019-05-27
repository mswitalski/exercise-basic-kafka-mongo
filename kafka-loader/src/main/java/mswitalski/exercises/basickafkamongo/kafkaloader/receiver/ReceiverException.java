package mswitalski.exercises.basickafkamongo.kafkaloader.receiver;

/**
 * Receiver exception that is to be thrown when any interaction between
 * the application and datasource results in error.
 */
public class ReceiverException extends Exception {

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }
}
