package mswitalski.exercises.basickafkamongo.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Class responsible for reading resource files holding properties <b>that are stored under classpath</b>.
 */
@Slf4j
public class PropertyReader {

    public Properties getPropertiesByFilename(String filename) {
        Properties properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream(filename));

            return properties;

        } catch (FileNotFoundException e) {
            throw new PropertyFileException("Chosen property file does not exist", e);

        } catch (IOException e) {
            throw new PropertyFileException("An error occurred while reading property file from filesystem", e);
        }
    }
}
