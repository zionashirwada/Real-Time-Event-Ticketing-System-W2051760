// src/main/java/lk/W2051760/ticketing_system_backend/controller/SystemController.java

package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.SystemState;
import lk.W2051760.ticketing_system_backend.service.SystemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemManagementService systemManagementService;

    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        try {
            systemManagementService.startSystem();
            return ResponseEntity.ok("System started successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/pause")
    public ResponseEntity<String> pauseSystem() {
        if (systemManagementService.getCurrentState() == SystemState.NOT_CONFIGURED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System is not configured.");
        }
        systemManagementService.pauseSystem();
        return ResponseEntity.ok("System paused successfully.");
    }

    @PostMapping("/stop-reset")
    public ResponseEntity<String> stopAndResetSystem() {
        if (systemManagementService.getCurrentState() == SystemState.NOT_CONFIGURED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System is not configured.");
        }
        systemManagementService.stopAndResetSystem();
        return ResponseEntity.ok("System stopped and reset successfully.");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getSystemStatus() {
        return ResponseEntity.ok(systemManagementService.getCurrentState().toString());
    }
}
