package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.producer.VendorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorManager vendorManager;

    // Get current vendor count
    @GetMapping("/count")
    public ResponseEntity<Integer> getVendorCount() {
        int count = vendorManager.getVendorCount();
        return ResponseEntity.ok(count);
    }

    // Add a vendor
    @PostMapping("/add")
    public ResponseEntity<String> addVendor() {
        vendorManager.addVendor();
        return ResponseEntity.ok("Vendor added successfully.");
    }

    // Remove a vendor
    @PostMapping("/remove")
    public ResponseEntity<String> removeVendor() {
        vendorManager.removeVendor();
        return ResponseEntity.ok("Vendor removed successfully.");
    }
    @PostMapping("/pause")
    public ResponseEntity<String> pauseVendors() {
        vendorManager.pauseVendors();
        return ResponseEntity.ok("Vendor action Paused.");
    }

    @PostMapping("/resume")
    public ResponseEntity<String> resumeVendors() {
        vendorManager.resumeVendors();
        return ResponseEntity.ok("Vendor action Resumed.");
    }
}