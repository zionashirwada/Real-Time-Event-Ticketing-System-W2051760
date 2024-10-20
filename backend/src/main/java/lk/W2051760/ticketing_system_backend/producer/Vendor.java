package lk.W2051760.ticketing_system_backend.producer;

import lk.W2051760.ticketing_system_backend.controller.TicketUpdateController;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

public class Vendor implements Runnable {

    private static final Logger logger = LogManager.getLogger(Vendor.class);

    private String vendorName;
    private int ticketsToRelease;
    private TicketPool ticketPool;
    private TicketUpdateController ticketUpdateController;
    private volatile boolean running;

    public Vendor(String vendorName, int ticketsToRelease, TicketPool ticketPool, TicketUpdateController ticketUpdateController) {
        this.vendorName = vendorName;
        this.ticketsToRelease = ticketsToRelease;
        this.ticketPool = ticketPool;
        this.ticketUpdateController = ticketUpdateController;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // add tickets to the pool
                int addedTickets = ticketPool.addTickets(ticketsToRelease);
                if (addedTickets > 0) {
                    logger.info("{} successfully released {} tickets.", vendorName, addedTickets);

                    //  WebSocket - send
                    TicketUpdate update = new TicketUpdate("ADD", "VENDOR", vendorName, addedTickets, ticketPool.getTotalTickets());
                    ticketUpdateController.sendTicketUpdate(update);
                } else {
                    logger.warn("{} failed to release {} tickets. Pool is full.", vendorName, ticketsToRelease);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("ADD_FAILED", "VENDOR", vendorName, ticketsToRelease, ticketPool.getTotalTickets());
                    ticketUpdateController.sendTicketUpdate(update);
                }

                //pause time
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", vendorName, e);
            } catch (Exception e) {
                logger.error("Unexpected error in Vendor {}: {}", vendorName, e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", vendorName);
    }

    public void stop() {
        running = false;
    }
}
