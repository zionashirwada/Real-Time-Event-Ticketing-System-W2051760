package lk.W2051760.ticketing_system_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.W2051760.ticketing_system_backend.model.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ConfigurationService {
    private static final Logger logger = LogManager.getLogger(ConfigurationService.class);
    private static final String CONFIG_PATH = "config/configuration.json"; // Root-level config directory

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
            if (config.getTotalSystemTickets() <= 0 || config.getMaxTicketCapacity() <= 0
                    || config.getTicketReleaseRate() <= 0 || config.getCustomerRetrievalRate() <= 0) {
                logger.warn("Invalid configuration values: all values must be positive");
                return null;
            }

            if (config.getMaxTicketCapacity() > config.getTotalSystemTickets()) {
                logger.warn("Invalid configuration: Max capacity cannot be greater than total system tickets");
                return null;
            }

            return config;
        } catch (Exception e) {
            logger.warn("Error loading configuration: {}", e.getMessage(), e);
            return null;
        }
    }

    public void saveConfiguration(Configuration configuration) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = Paths.get(CONFIG_PATH).toFile();

            // Ensure the config directory exists
            File configDir = file.getParentFile();
            if (!configDir.exists()) {
                configDir.mkdirs();
            }

            mapper.writeValue(file, configuration);
            logger.info("Configuration saved successfully at {}", CONFIG_PATH);
        } catch (Exception e) {
            logger.error("Error saving configuration: {}", e.getMessage(), e);
            throw e;
        }
    }
}
