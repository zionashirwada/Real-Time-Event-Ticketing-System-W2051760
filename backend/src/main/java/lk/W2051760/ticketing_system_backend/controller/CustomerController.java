package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.consumer.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerManager customerManager;

    // Get current customer count
    @GetMapping("/count")
    public ResponseEntity<Integer> getCustomerCount() {
        int count = customerManager.getCustomerCount();
        return ResponseEntity.ok(count);
    }

    // Add a customer
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer() {
        customerManager.addCustomer();
        return ResponseEntity.ok("Customer added successfully.");
    }

    // Remove a customer
    @PostMapping("/remove")
    public ResponseEntity<String> removeCustomer() {
        customerManager.removeCustomer();
        return ResponseEntity.ok("Customer removed successfully.");
    }

    @PostMapping("/pause")
    public ResponseEntity<String> pauseCustomers() {
        customerManager.pauseCustomers();
        return ResponseEntity.ok("Customer action Paused.");
    }

    @PostMapping("/resume")
    public ResponseEntity<String> resumeCustomers() {
        customerManager.resumeCustomers();
        return ResponseEntity.ok("Customer action Resumed.");
    }
}