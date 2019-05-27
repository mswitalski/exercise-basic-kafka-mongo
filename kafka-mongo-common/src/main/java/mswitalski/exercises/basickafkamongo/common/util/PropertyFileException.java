package mswitalski.exercises.basickafkamongo.common.util;

/**
 * Property file reader exception that is to be thrown when chosen resource file
 * could not be properly read.
 */
public class PropertyFileException extends RuntimeException {

    public PropertyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
