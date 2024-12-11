/**
 * ConfigurationService.java
 *
 * This file contains the ConfigurationService class, which provides methods 
 * for managing system configurations. It allows for loading and saving 
 * configuration data to and from a JSON file.
 */
package lk.W2051760.ticketing_system_backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lk.W2051760.ticketing_system_backend.model.Configuration;

@Service
public class ConfigurationService {
    private static final Logger logger = LogManager.getLogger(ConfigurationService.class);
    private static final String CONFIG_PATH = "config/configuration.json";

    /**
     * Loads the configuration from a JSON file.
     *
     * This method attempts to read the configuration from the specified JSON file,
     * validates the configuration values, and returns the Configuration object if
     * the values are valid.
     *
     * @return Configuration object if successfully loaded and valid, otherwise
     *         null.
     * @throws IOException if an I/O error occurs reading from the file or a
     *                     malformed or unmappable byte sequence is read.
     */
    public Configuration loadConfiguration() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = Paths.get(CONFIG_PATH).toFile();

            if (!file.exists()) {
                logger.warn("Configuration file not found at: {}", CONFIG_PATH);
                return null;
            }

            Configuration config = mapper.readValue(file, Configuration.class);

            // Validate configuration
            if (!isValidConfiguration(config)) {
                logger.warn("Invalid configuration values: Validation failed during load.");
                return null;
            }

            return config;
        } catch (Exception e) {
            logger.warn("Error loading configuration: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Saves the given configuration to a JSON file.
     *
     * This method serializes the provided Configuration object into JSON format
     * and writes it to the specified file. If the file or its directory does not
     * exist, it creates the necessary directories.
     *
     * @param configuration The Configuration object to be saved.
     * @throws IOException if an I/O error occurs writing to the file.
     */
    public void saveConfiguration(Configuration configuration) throws IOException {
        try {
            if (!isValidConfiguration(configuration)) {
                throw new IllegalArgumentException("Invalid configuration values: Validation failed during save.");
            }

            ObjectMapper mapper = new ObjectMapper();
            File file = Paths.get(CONFIG_PATH).toFile();

            // Ensure the config directory exists
            File configDir = file.getParentFile();
            if (!configDir.exists()) {
                configDir.mkdirs();
            }

            mapper.writeValue(file, configuration);
            logger.info("Configuration saved successfully at {}", CONFIG_PATH);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error saving configuration: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Validates the given Configuration object.
     *
     * This method checks that all fields are positive integers and that
     * maxTicketCapacity is less than or equal to totalSystemTickets.
     *
     * @param config The Configuration object to validate.
     * @return true if the configuration is valid, false otherwise.
     */
    private boolean isValidConfiguration(Configuration config) {
        if (config.getTotalSystemTickets() <= 0 || config.getMaxTicketCapacity() <= 0
                || config.getTicketReleaseRate() <= 0 || config.getCustomerRetrievalRate() <= 0) {
            logger.error("Validation failed: All values must be positive.");
            return false;
        }

        if (config.getMaxTicketCapacity() > config.getTotalSystemTickets()) {
            logger.error("Validation failed: Max capacity cannot be greater than total system tickets.");
            return false;
        }

        return true;
    }
}
