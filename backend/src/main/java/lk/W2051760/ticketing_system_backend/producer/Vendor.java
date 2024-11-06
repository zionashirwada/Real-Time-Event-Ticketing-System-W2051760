package lk.W2051760.ticketing_system_backend.producer;
// 
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.model.TransactionLog;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vendor implements Runnable {

    private static final Logger logger = LogManager.getLogger(Vendor.class);

    private String vendorName;
    private int ticketsToRelease;
    private TicketPool ticketPool;
    private volatile boolean running;
    private final Object pauseLock = new Object();
    private volatile boolean paused = false;
    private TicketUpdateService ticketUpdateService;
    private final TransactionLogService transactionLogService;


    public Vendor(String vendorName, int ticketsToRelease, TicketPool ticketPool,
                  TicketUpdateService ticketUpdateService,TransactionLogService transactionLogService) {
        this.vendorName = vendorName;
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
                    logger.info("{} successfully released {} tickets.", vendorName, addedTickets);

                    //  WebSocket - send
                    TicketUpdate update = new TicketUpdate("ADD", "VENDOR", vendorName, addedTickets,
                            ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                    TransactionLog log = new TransactionLog("ADD", "VENDOR", vendorName, addedTickets, ticketPool.getPoolTicketAmount());
                    transactionLogService.sendTransactionLog(log);
                } else {
                    logger.warn("{} failed to release {} tickets. Pool is full.", vendorName, ticketsToRelease);

                    // Send WebSocket
                    TicketUpdate update = new TicketUpdate("ADD_FAILED", "VENDOR", vendorName,
                            ticketsToRelease, ticketPool.getPoolTicketAmount());
                    ticketUpdateService.sendTicketUpdate(update);
                }

                //pause time
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", vendorName, e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in Vendor {}: {}", vendorName, e.getMessage(), e);
            }
        }
        logger.info("{} has stopped.", vendorName);
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