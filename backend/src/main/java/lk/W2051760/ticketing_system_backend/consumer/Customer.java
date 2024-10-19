package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {
    private static final Logger logger = LogManager.getLogger(Customer.class);
    private String customerName;
    private int ticketsToPurchase;
    private TicketPool ticketPool;
    private volatile boolean running;

    public Customer(String customerName, int ticketsToPurchase, TicketPool ticketPool) {
        this.customerName = customerName;
        this.ticketsToPurchase = ticketsToPurchase;
        this.ticketPool = ticketPool;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Attempt to purchase tickets
                boolean success = ticketPool.removeTickets(ticketsToPurchase);
                if (success) {
                    logger.info("{} successfully purchased {} tickets.", customerName, ticketsToPurchase);
                } else {
                    logger.warn("{} failed to purchase {} tickets. Not enough tickets available.", customerName, ticketsToPurchase);
                }
                // pause amount
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", customerName, e);
            }
        }
        logger.info("{} has stopped.", customerName);
    }

    public void stop() {

        running = false;
    }
}
