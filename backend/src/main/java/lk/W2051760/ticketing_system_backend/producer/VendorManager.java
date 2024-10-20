package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendorManager {

    private static final Logger logger = LogManager.getLogger(VendorManager.class);

    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Vendor> vendors = new ArrayList<>();
    private final TicketPool ticketPool;
    private final TicketUpdateService ticketUpdateService;

    private int ticketsToRelease; // Number of tickets each vendor releases

    public VendorManager(TicketPool ticketPool, TicketUpdateService ticketUpdateService) {
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
    }

    // Initialize with initial vendor count and tickets to release
    public synchronized void initialize(int initialVendorCount, int ticketsToRelease) {
        this.ticketsToRelease = ticketsToRelease;
        for (int i = 0; i < initialVendorCount; i++) {
            addVendorThread();
        }
    }

    // Start all vendors
    public synchronized void startVendors() {
        for (Thread thread : vendorThreads) {
            if (!thread.isAlive()) {
                thread.start();
            }
        }
        logger.info("All vendor threads have been started.");
    }

    // Pause all vendors
    public synchronized void pauseVendors() {
        for (Vendor vendor : vendors) {
            vendor.pause();
        }
        logger.info("All vendor threads have been requested to pause.");
    }

    // Resume all vendors
    public synchronized void resumeVendors() {
        for (Vendor vendor : vendors) {
            vendor.resume();
        }
        logger.info("All vendor threads have been requested to resume.");
    }

    // Get the current number of vendors
    public synchronized int getVendorCount() {
        return vendors.size();
    }

    // Add a vendor
    public synchronized void addVendor() {
        addVendorThread();
        logger.info("Vendor added. Total vendors: {}", getVendorCount());
    }

    // Remove a vendor
    public synchronized void removeVendor() {
        if (!vendors.isEmpty()) {
            int lastIndex = vendors.size() - 1;
            Vendor vendor = vendors.remove(lastIndex);
            vendor.stop();
            vendorThreads.remove(lastIndex);
            logger.info("Vendor removed. Total vendors: {}", getVendorCount());
        } else {
            logger.warn("No vendors to remove.");
        }
    }

    // Stop all vendors
    public synchronized void stopAllVendors() {
        for (Vendor vendor : vendors) {
            vendor.stop();
        }
        vendors.clear();
        vendorThreads.clear();
        logger.info("All vendor threads have been stopped.");
    }

    private void addVendorThread() {
        String vendorName = "Vendor " + (vendors.size() + 1);
        Vendor vendor = new Vendor(vendorName, ticketsToRelease, ticketPool, ticketUpdateService);
        Thread thread = new Thread(vendor, vendorName + "-Thread");
        thread.setDaemon(true);
        vendors.add(vendor);
        vendorThreads.add(thread);
        thread.start();
        logger.info("Started {}", thread.getName());
    }
}
