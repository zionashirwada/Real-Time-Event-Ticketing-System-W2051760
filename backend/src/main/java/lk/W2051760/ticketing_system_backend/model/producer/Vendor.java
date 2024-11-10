package lk.W2051760.ticketing_system_backend.model.producer;
// 
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.model.TransactionLog;
import lk.W2051760.ticketing_system_backend.model.User;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vendor extends User implements Runnable {

    private static final Logger logger = LogManager.getLogger(Vendor.class);
    
    private int ticketsToRelease;
    private TicketPool ticketPool;
    private volatile boolean running;
    private final Object pauseLock = new Object();
    private volatile boolean paused = false;
    private TicketUpdateService ticketUpdateService;
    private final TransactionLogService transactionLogService;


    public Vendor(int id, String name, int ticketsToRelease, TicketPool ticketPool,
                  TicketUpdateService ticketUpdateService, TransactionLogService transactionLogService) {
        super(id, name);
        this.ticketsToRelease = ticketsToRelease;
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
                // add tickets to the pool
                int addedTickets = ticketPool.addTickets(ticketsToRelease);
                if (addedTickets > 0) {
                    logger.info("{} successfully released {} tickets.", getName(), addedTickets);

                    //  WebSocket - send
                    TicketUpdate update = new TicketUpdate("ADD", "VENDOR", getName(), addedTickets,
                            ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                    TransactionLog log = new TransactionLog("ADD", "VENDOR", getName(), addedTickets, ticketPool.getPoolTicketAmount());
                    transactionLogService.sendTransactionLog(log);
                } else {
                    logger.warn("{} failed to release {} tickets. Pool is full.", getName(), ticketsToRelease);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("ADD_FAILED", "VENDOR", getName(),
                            ticketsToRelease, ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                }

                //pause time
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", getName(), e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in Vendor {}: {}", getName(), e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", getName());
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