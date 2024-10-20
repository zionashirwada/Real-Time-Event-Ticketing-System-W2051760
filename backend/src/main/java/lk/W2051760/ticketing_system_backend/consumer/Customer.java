package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.controller.TicketUpdateController;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

public class Customer implements Runnable {

    private static final Logger logger = LogManager.getLogger(Customer.class);

    private String customerName;
    private int ticketsToPurchase;
    private TicketPool ticketPool;
    private TicketUpdateController ticketUpdateController;
    private volatile boolean running;

    public Customer(String customerName, int ticketsToPurchase, TicketPool ticketPool, TicketUpdateController ticketUpdateController) {
        this.customerName = customerName;
        this.ticketsToPurchase = ticketsToPurchase;
        this.ticketPool = ticketPool;
        this.ticketUpdateController = ticketUpdateController;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // purchase tickets
                boolean success = ticketPool.removeTickets(ticketsToPurchase);
                if (success) {
                    logger.info("{} successfully purchased {} tickets.", customerName, ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE", "CUSTOMER", customerName, ticketsToPurchase, ticketPool.getTotalTickets());
                    ticketUpdateController.sendTicketUpdate(update);
                } else {
                    logger.warn("{} failed to purchase {} tickets. Not enough tickets available.", customerName, ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE_FAILED", "CUSTOMER", customerName, ticketsToPurchase, ticketPool.getTotalTickets());
                    ticketUpdateController.sendTicketUpdate(update);
                }

                //pause time
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", customerName, e);
            } catch (Exception e) {
                logger.error("Unexpected error in Customer {}: {}", customerName, e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", customerName);
    }

    public void stop() {
        running = false;
    }
}
