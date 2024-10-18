package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.Configuration;
import lk.W2051760.ticketing_system_backend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("/configuration")
    public String saveConfiguration(@RequestBody Configuration config) {
        try {
            configurationService.saveConfiguration(config);
            return "Configuration saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving configuration";
        }
    }

    @GetMapping("/configuration")
    public Configuration getConfiguration() {
        try {
            return configurationService.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            return new Configuration();
        }
    }
}
