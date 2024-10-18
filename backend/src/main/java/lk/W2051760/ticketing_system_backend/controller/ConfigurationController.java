package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private Configuration config = new Configuration(); // In-memory storage for now

    @PostMapping
    public Configuration setConfiguration(@RequestBody Configuration newConfig) {
        config = newConfig;
        return config;
    }

    @GetMapping
    public Configuration getConfiguration() {
        return config;
    }
}
