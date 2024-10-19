package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;

public class Vendor implements Runnable {

    private String vendorName;
    private int ticketReleaseRate;
    private TicketPool ticketPool;
    private volatile boolean running;

    public Vendor(String vendorName, int ticketReleaseRate, TicketPool ticketPool) {
        this.vendorName = vendorName;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Add tickets to the ticket pool
                ticketPool.addTickets(ticketReleaseRate);
                System.out.println(vendorName + " released " + ticketReleaseRate + " tickets.");

                // Wait for a certain interval before releasing more tickets
                Thread.sleep(1000); // Sleep for 1 second (adjust as needed)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendorName + " was interrupted.");
            }
        }
    }

    public void stop() {
        running = false;
    }
}
