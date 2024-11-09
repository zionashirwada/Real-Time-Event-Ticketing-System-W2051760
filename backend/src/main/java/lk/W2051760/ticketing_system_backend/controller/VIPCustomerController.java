package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.consumer.VIPCustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vip-customers")
public class VIPCustomerController {

    @Autowired
    private VIPCustomerManager vipCustomerManager;

    @GetMapping("/count")
    public ResponseEntity<Integer> getVIPCustomerCount() {
        int count = vipCustomerManager.getVIPCustomerCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addVIPCustomer() {
        vipCustomerManager.addVIPCustomer();
        return ResponseEntity.ok("VIP Customer added successfully.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeVIPCustomer() {
        vipCustomerManager.removeVIPCustomer();
        return ResponseEntity.ok("VIP Customer removed successfully.");
    }

    @PostMapping("/pause")
    public ResponseEntity<String> pauseVIPCustomers() {
        vipCustomerManager.pauseVIPCustomers();
        return ResponseEntity.ok("VIP Customer action Paused.");
    }

    @PostMapping("/resume")
    public ResponseEntity<String> resumeVIPCustomers() {
        vipCustomerManager.resumeVIPCustomers();
        return ResponseEntity.ok("VIP Customer action Resumed.");
    }
} 