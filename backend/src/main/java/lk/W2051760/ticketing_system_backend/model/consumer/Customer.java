package lk.W2051760.ticketing_system_backend.model.consumer;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.model.TransactionLog;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {

    private static final Logger logger = LogManager.getLogger(Customer.class);

    private String customerName;
    private int ticketsToPurchase;
    private TicketPool ticketPool;
    private volatile boolean running;
    private final Object pauseLock = new Object();
    private volatile boolean paused = false;
    private TicketUpdateService ticketUpdateService;
    private final TransactionLogService transactionLogService;


    public Customer(String customerName, int ticketsToPurchase, TicketPool ticketPool,
                    TicketUpdateService ticketUpdateService,TransactionLogService transactionLogService) {
        this.customerName = customerName;
        this.ticketsToPurchase = ticketsToPurchase;
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
        this.transactionLogService = transactionLogService;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (pauseLock) {
                    while (paused) {
                        pauseLock.wait();
                    }
                }
                // purchase tickets
                boolean success = ticketPool.removeTickets(ticketsToPurchase);
                if (success) {
                    logger.info("{} successfully purchased {} tickets.", customerName, ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE", "CUSTOMER", customerName, ticketsToPurchase, ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                    TransactionLog log = new TransactionLog("REMOVE", "CUSTOMER", customerName, ticketsToPurchase, ticketPool.getPoolTicketAmount());
                    transactionLogService.sendTransactionLog(log);
                } else {
                    logger.warn("{} failed to purchase {} tickets. Not enough tickets available.", customerName, ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE_FAILED", "CUSTOMER", customerName, ticketsToPurchase, ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                }

                //pause time
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", customerName, e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in Customer {}: {}", customerName, e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", customerName);
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void stop() {
        running = false;
        resume();
    }
}