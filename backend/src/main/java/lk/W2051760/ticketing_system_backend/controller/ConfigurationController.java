package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.Configuration;
import lk.W2051760.ticketing_system_backend.service.ConfigurationService;
import lk.W2051760.ticketing_system_backend.service.SystemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private SystemManagementService systemManagementService;

    
    /** 
     * @param config
     * @return ResponseEntity<String>
     */
    @PostMapping("/configuration")
    public ResponseEntity<String> saveConfiguration(@RequestBody Configuration config) {
        try {
            configurationService.saveConfiguration(config);
            return ResponseEntity.ok("Configuration saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving configuration: " + e.getMessage());
        }
    }

    @GetMapping("/configuration")
    public ResponseEntity<Object> getConfiguration() {
        try {
            Configuration config = configurationService.loadConfiguration();
            if (config == null) {
                return ResponseEntity.status(404).body("System not configured");
            }
            return ResponseEntity.ok(config);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error loading configuration");
        }
    }

    @PostMapping("/configuration/reload")
    public ResponseEntity<String> reloadSystem() {
        try {
            Configuration config = configurationService.loadConfiguration();
            if (config == null) {
                return ResponseEntity.status(404).body("System not configured");
            }
            
            // Reinitialize system with new configuration
            systemManagementService.reinitializeSystem(config);
            return ResponseEntity.ok("System reinitialized successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reloading system: " + e.getMessage());
        }
    }
}
