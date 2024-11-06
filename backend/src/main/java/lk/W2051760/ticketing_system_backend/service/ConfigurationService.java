package lk.W2051760.ticketing_system_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.W2051760.ticketing_system_backend.model.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Service
public class ConfigurationService {
    private static final Logger logger = LogManager.getLogger(ConfigurationService.class);
    private static final String CONFIG_FILE = "configuration.json";

    public Configuration loadConfiguration() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = ResourceUtils.getFile("classpath:" + CONFIG_FILE);
            Configuration config = mapper.readValue(file, Configuration.class);
            
            // Validate configuration
            if (config.getTotalSystemTickets() <= 0 || config.getMaxTicketCapacity() <= 0 
                || config.getTicketReleaseRate() <= 0 || config.getCustomerRetrievalRate() <= 0) {
                throw new IllegalArgumentException("All configuration values must be positive");
            }
            
            if (config.getMaxTicketCapacity() > config.getTotalSystemTickets()) {
                throw new IllegalArgumentException("Max capacity cannot be greater than total system tickets");
            }
            
            return config;
        } catch (Exception e) {
            logger.error("Error loading configuration: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void saveConfiguration(Configuration configuration) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = ResourceUtils.getFile("classpath:" + CONFIG_FILE);
            mapper.writeValue(file, configuration);
            logger.info("Configuration saved successfully");
        } catch (Exception e) {
            logger.error("Error saving configuration: {}", e.getMessage(), e);
            throw e;
        }
    }
}
