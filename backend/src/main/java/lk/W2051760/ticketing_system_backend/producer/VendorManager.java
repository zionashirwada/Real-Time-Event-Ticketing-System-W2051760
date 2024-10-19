package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendorManager implements DisposableBean{
    private static final Logger logger = LogManager.getLogger(VendorManager.class);
    private List<Thread> vendorThreads;
    private List<Vendor> vendors;
    private TicketPool ticketPool;

    public VendorManager(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.vendorThreads = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

    public void initialize(int numberOfVendors, int ticketReleaseRate) {
        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor " + i, ticketReleaseRate, ticketPool);
            Thread thread = new Thread(vendor, "VendorThread-" + i);
            thread.setDaemon(true);
            vendors.add(vendor);
            vendorThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
        }
    }

    public void startVendors() {
        for (Thread thread : vendorThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

    public void stopVendors() {
        for (Vendor vendor : vendors) {
            vendor.stop();
        }
        logger.info("All vendor threads have been requested to stop.");
    }
    @Override
    public void destroy() throws Exception {
        stopVendors();
        logger.info("VendorManager is being destroyed. Vendor threads stopped.");
    }


}

