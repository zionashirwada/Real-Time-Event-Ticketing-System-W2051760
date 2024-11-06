package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.Ticket;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TicketPool {
    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    private BlockingQueue<Ticket> ticketPool;
    private int maxTicketCapacity;
    private int totalSystemTickets;
    private volatile int totalReleasedTickets = 0;

    @Autowired
    private TicketUpdateService ticketUpdateService;

    public TicketPool() {
        this.ticketPool = new LinkedBlockingQueue<>();
    }

    public synchronized void initialize(int maxTicketCapacity, int totalSystemTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalSystemTickets = totalSystemTickets;
        this.totalReleasedTickets = 0;
        this.ticketPool.clear();
    }

    public synchronized int addTickets(int amount) {
        // Check if adding these tickets would exceed total system tickets
        if (totalReleasedTickets >= totalSystemTickets) {
            logger.warn("Cannot add more tickets: Total system tickets limit ({}) reached", totalSystemTickets);
            return 0;
        }

        // Calculate how many tickets we can actually add
        int remainingSystemTickets = totalSystemTickets - totalReleasedTickets;
        int availablePoolSpace = maxTicketCapacity - ticketPool.size();
        int actualAmount = Math.min(Math.min(amount, remainingSystemTickets), availablePoolSpace);

        if (actualAmount <= 0) {
            return 0;
        }

        // Add the tickets
        for (int i = 0; i < actualAmount; i++) {
            ticketPool.offer(new Ticket(totalReleasedTickets + i + 1));
        }
        totalReleasedTickets += actualAmount;
        
        return actualAmount;
    }

    public synchronized boolean removeTickets(int amount) {
        if (ticketPool.size() < amount) {
            return false;
        }

        for (int i = 0; i < amount; i++) {
            ticketPool.poll();
        }
        return true;
    }

    public synchronized void reset() {
        ticketPool.clear();
        totalReleasedTickets = 0;
        // Send WebSocket update about the reset
        TicketUpdate update = new TicketUpdate(
            "RESET",
            "SYSTEM",
            "System",
            0,
            0
        );
        ticketUpdateService.sendTicketUpdate(update);
        logger.info("Ticket pool has been reset");
    }

    public synchronized void stopAndReset() {
        reset();
        // Send WebSocket update about the stop and reset
        TicketUpdate update = new TicketUpdate(
            "STOP_RESET",
            "SYSTEM",
            "System",
            0,
            0
        );
        ticketUpdateService.sendTicketUpdate(update);
        logger.info("Ticket pool has been stopped and reset");
    }

    public synchronized int getPoolTicketAmount() {
        return ticketPool.size();
    }

    public synchronized int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public synchronized int getTotalReleasedTickets() {
        return totalReleasedTickets;
    }

    public synchronized int getTotalSystemTickets() {
        return totalSystemTickets;
    }
}

