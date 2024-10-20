package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.controller.TicketUpdateController;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
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
    private final TicketUpdateController ticketUpdateController;

    public VendorManager(TicketPool ticketPool, TicketUpdateController ticketUpdateController) {
        this.ticketPool = ticketPool;
        this.ticketUpdateController = ticketUpdateController;
        this.vendorThreads = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

//   initialize
    public void initialize(int numberOfVendors, int ticketsToRelease) {
        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor " + i, ticketsToRelease, ticketPool, ticketUpdateController);
            Thread thread = new Thread(vendor, "VendorThread-" + i);
            thread.setDaemon(true); // Set as daemon to allow JVM to exit if only daemon threads are running
            vendors.add(vendor);
            vendorThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
        }
    }

//  Start vendors
    public void startVendors() {
        for (Thread thread : vendorThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

//    Stop
    public void stopVendors() {
        for (Vendor vendor : vendors) {
            vendor.stop();
        }
        logger.info("All vendor threads have been requested to stop.");
    }

//    called before destructed
    @PreDestroy
    public void onDestroy() {
        stopVendors();
        logger.info("VendorManager is being destroyed. Vendor threads stopped.");
    }
}
