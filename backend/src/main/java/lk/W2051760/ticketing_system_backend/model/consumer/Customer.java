/**
 * Customer.java
 * 
 * This file contains the Customer class, which represents a customer in the ticketing system.
 * Customers can purchase tickets from the TicketPool, pause/resume their actions, and stop 
 * their execution. The class also integrates with TicketUpdateService and TransactionLogService
 * to broadcast updates and log transactions.
 */
package lk.W2051760.ticketing_system_backend.model.consumer;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.model.TransactionLog;
import lk.W2051760.ticketing_system_backend.model.User;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Constructs a Customer object with the specified details and dependencies.
 * 
 * @param id                    the unique identifier for the customer.
 * @param name                  the name of the customer.
 * @param ticketsToPurchase     the number of tickets the customer intends to
 *                              purchase.
 * @param ticketPool            the TicketPool from which tickets are purchased.
 * @param ticketUpdateService   the service used to send ticket updates via
 *                              WebSocket.
 * @param transactionLogService the service used to log transactions.
 */
public class Customer extends User implements Runnable {

    private static final Logger logger = LogManager.getLogger(Customer.class);

    private int ticketsToPurchase;
    private TicketPool ticketPool;
    private volatile boolean running;
    private final Object pauseLock = new Object();
    private volatile boolean paused = false;
    private TicketUpdateService ticketUpdateService;
    private final TransactionLogService transactionLogService;

    public Customer(int id, String name, int ticketsToPurchase, TicketPool ticketPool,
            TicketUpdateService ticketUpdateService, TransactionLogService transactionLogService) {
        super(id, name);
        this.ticketsToPurchase = ticketsToPurchase;
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
        this.transactionLogService = transactionLogService;
        this.running = true;
    }

    /**
     * The main execution loop for the customer.
     * 
     * This method attempts to purchase tickets at regular intervals, handles
     * pause/resume
     * functionality, and broadcasts updates or logs based on transaction outcomes.
     */
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
                    logger.info("{} successfully purchased {} tickets.", getName(), ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE", "CUSTOMER", getName(), ticketsToPurchase,
                            ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                    TransactionLog log = new TransactionLog("REMOVE", "CUSTOMER", getName(), ticketsToPurchase,
                            ticketPool.getPoolTicketAmount());
                    transactionLogService.sendTransactionLog(log);
                } else {
                    logger.warn("{} failed to purchase {} tickets. Not enough tickets available.", getName(),
                            ticketsToPurchase);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("REMOVE_FAILED", "CUSTOMER", getName(), ticketsToPurchase,
                            ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                }

                // pause time
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", getName(), e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in Customer {}: {}", getName(), e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", getName());
    }

    /**
     * Pauses the customer's actions.
     * 
     * This method sets the paused state to true, halting the customer's execution
     * until resumed.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resumes the customer's actions.
     * 
     * This method resets the paused state and notifies the customer's execution
     * thread to continue.
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    /**
     * Stops the customer's execution.
     * 
     * This method sets the running state to false and resumes the thread to allow
     * it to exit gracefully.
     */
    public void stop() {
        running = false;
        resume();
    }
}