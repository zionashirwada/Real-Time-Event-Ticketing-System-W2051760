
package lk.W2051760.ticketing_system_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.W2051760.ticketing_system_backend.model.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ConfigurationService {
    private final String CONFIG_FILE = "configuration.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveConfiguration(Configuration config) throws IOException {
        objectMapper.writeValue(new File(CONFIG_FILE), config);
    }

    public Configuration loadConfiguration() throws IOException {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            return objectMapper.readValue(file, Configuration.class);
        } else {
            return null;
        }
    }
}
