package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import java.util.ArrayList;
import java.util.List;

public class VendorManager {

    private List<Thread> vendorThreads;
    private List<Vendor> vendors;
    private TicketPool ticketPool;

    public VendorManager(TicketPool ticketPool, int numberOfVendors, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.vendorThreads = new ArrayList<>();
        this.vendors = new ArrayList<>();

        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor " + i, ticketReleaseRate, ticketPool);
            Thread thread = new Thread(vendor, "VendorThread-" + i);
            vendors.add(vendor);
            vendorThreads.add(thread);
        }
    }

    public void startVendors() {
        for (Thread thread : vendorThreads) {
            thread.start();
        }
    }

    public void stopVendors() {
        for (Vendor vendor : vendors) {
            vendor.stop();
        }
    }
    
}
