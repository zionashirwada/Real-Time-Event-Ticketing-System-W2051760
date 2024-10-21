package lk.W2051760.ticketing_system_backend.producer;


import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendorManager {

    private static final Logger logger = LogManager.getLogger(VendorManager.class);

    private List<Thread> vendorThreads;
    private List<Vendor> vendors;
    private TicketPool ticketPool;
    private final TicketUpdateService ticketUpdateService;

    private int numberOfVendors;
    private int ticketsToRelease;
    public VendorManager(TicketPool ticketPool, TicketUpdateService ticketUpdateService) {
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
        this.vendorThreads = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

//   initialize
    public void initialize(int numberOfVendors, int ticketsToRelease) {
        this.numberOfVendors = numberOfVendors;
        this.ticketsToRelease = ticketsToRelease;
    }

//  Start vendors
    public void startVendors() {
        vendors.clear();
        vendorThreads.clear();
        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor " + i, ticketsToRelease, ticketPool, ticketUpdateService);
            Thread thread = new Thread(vendor, "VendorThread-" + i);
            thread.setDaemon(true);
            vendors.add(vendor);
            vendorThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
        }
        for (Thread thread : vendorThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

    public void pauseVendors() {
        for (Vendor vendor : vendors) {
            vendor.pause();
        }
        logger.info("All vendor threads have been requested to pause.");
    }

    public void resumeVendors() {
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
    public void stopVendors() {
        for (Vendor vendor : vendors) {
            vendor.stop();
        }
        logger.info("All vendor threads have been requested to stop.");
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

//    called before destructed
    @PreDestroy
    public void onDestroy() {
        stopVendors();
        logger.info("VendorManager is being destroyed. Vendor threads stopped.");
    }
}
