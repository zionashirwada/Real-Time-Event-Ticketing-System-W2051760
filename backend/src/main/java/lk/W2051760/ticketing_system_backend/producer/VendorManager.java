package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendorManager implements DisposableBean{

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
            thread.setDaemon(true); // Set thread as daemon
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
    @Override
    public void destroy() throws Exception {
        stopVendors();
        System.out.println("VendorManager destroyed. Vendor threads stopped.");
    }


}

