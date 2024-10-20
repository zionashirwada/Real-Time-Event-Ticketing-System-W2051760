package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TicketPool {
    private static final Logger logger = LogManager.getLogger(TicketPool.class);
    private int totalTickets;
    private int maxTicketCapacity;
    private final TicketUpdateService ticketUpdateService;

    public TicketPool(TicketUpdateService ticketUpdateService) {
        this.ticketUpdateService = ticketUpdateService;
    }

    public void initialize(int maxTicketCapacity) {
        this.totalTickets = 0;
        this.maxTicketCapacity = maxTicketCapacity;
        logger.info("TicketPool initialized with max capacity: {}", maxTicketCapacity);
    }
// Sync methods - add //reove
public synchronized int addTickets(int tickets) {
    try {
        if (tickets <= 0) {
            throw new IllegalArgumentException("Number of tickets to add must be positive.");
        }

        if (totalTickets + tickets <= maxTicketCapacity) {
            totalTickets += tickets;
            logger.info("Added {} tickets. Total tickets: {}", tickets, totalTickets);

            // Send WebSocket update
            TicketUpdate update = new TicketUpdate("ADD", "SYSTEM", "TicketPool", tickets, totalTickets);
            ticketUpdateService.sendTicketUpdate(update);

            return tickets;
        } else {
            int availableSpace = maxTicketCapacity - totalTickets;
            if (availableSpace > 0) {
                totalTickets += availableSpace;
                logger.warn("Cannot add {} tickets. Added only {} to reach max capacity ({}).", tickets, availableSpace, maxTicketCapacity);

                // Send WebSocket update
                TicketUpdate update = new TicketUpdate("ADD_PARTIAL", "SYSTEM", "TicketPool", availableSpace, totalTickets);
                ticketUpdateService.sendTicketUpdate(update);

                return availableSpace;
            }
            logger.warn("Cannot add {} tickets. Max capacity reached ({}).", tickets, maxTicketCapacity);

            // Send WebSocket update
            TicketUpdate update = new TicketUpdate("ADD_FAILED", "SYSTEM", "TicketPool", tickets, totalTickets);
            ticketUpdateService.sendTicketUpdate(update);

            return 0;
        }
    } catch (IllegalArgumentException e) {
        logger.error("Invalid argument in addTickets: {}", e.getMessage(), e);
        return 0;
    } catch (Exception e) {
        logger.error("Unexpected error in addTickets: {}", e.getMessage(), e);
        return 0;
    }
}

    public synchronized boolean removeTickets(int tickets) {
        try {
            if (tickets <= 0) {
                throw new IllegalArgumentException("Number of tickets to remove must be positive.");
            }

            if (tickets <= totalTickets) {
                totalTickets -= tickets;
                logger.info("Removed {} tickets. Total tickets: {}", tickets, totalTickets);

                // Send WebSocket update
                TicketUpdate update = new TicketUpdate("REMOVE", "SYSTEM", "TicketPool", tickets, totalTickets);
                ticketUpdateService.sendTicketUpdate(update);

                return true;
            } else {
                logger.warn("Cannot remove {} tickets. Only {} available.", tickets, totalTickets);

                // Send WebSocket update
                TicketUpdate update = new TicketUpdate("REMOVE_FAILED", "SYSTEM", "TicketPool", tickets, totalTickets);
                ticketUpdateService.sendTicketUpdate(update);

                return false;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument in removeTickets: {}", e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error in removeTickets: {}", e.getMessage(), e);
            return false;
        }
    }

    public synchronized int getTotalTickets() {
        try {
            return totalTickets;
        } catch (Exception e) {
            logger.error("Error while getting total tickets: {}", e.getMessage(), e);
            return -1;
        }
    }

    public synchronized void reset() {
        this.totalTickets = 0;
        logger.info("TicketPool has been reset.");
    }
    public synchronized int getMaxTicketCapacity() {
        try {
            return maxTicketCapacity;
        } catch (Exception e) {
            logger.error("Error while getting max ticket capacity: {}", e.getMessage(), e);
            return -1;
        }
    }
}
