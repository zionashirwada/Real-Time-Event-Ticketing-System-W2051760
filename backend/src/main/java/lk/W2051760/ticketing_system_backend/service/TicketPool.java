package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TicketPool {
    private static final Logger logger = LogManager.getLogger(TicketPool.class);
    private int poolTicketAmount;
    private int maxTicketCapacity;
    private int totalReleasedTickets;
    private int totalSystemTickets;
    private final TicketUpdateService ticketUpdateService;

    public TicketPool(TicketUpdateService ticketUpdateService) {
        this.ticketUpdateService = ticketUpdateService;
    }

    public void initialize(int maxTicketCapacity, int totalSystemTickets) {
        this.poolTicketAmount = 0;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalSystemTickets = totalSystemTickets;
        this.totalReleasedTickets = 0;
        logger.info("TicketPool initialized with max capacity: {}, total system tickets: {}", 
            maxTicketCapacity, totalSystemTickets);
    }

    public synchronized int addTickets(int tickets) {
        try {
            if (tickets <= 0) {
                throw new IllegalArgumentException("Number of tickets to add must be positive.");
            }

            // Check if we've reached total system limit
            if (totalReleasedTickets >= totalSystemTickets) {
                logger.warn("Cannot add more tickets. Total system limit reached ({}).", totalSystemTickets);
                return 0;
            }

            // Calculate how many tickets we can still add to the system
            int remainingSystemCapacity = totalSystemTickets - totalReleasedTickets;
            int ticketsToAdd = Math.min(tickets, remainingSystemCapacity);

            // Check pool capacity
            if (poolTicketAmount + ticketsToAdd <= maxTicketCapacity) {
                poolTicketAmount += ticketsToAdd;
                totalReleasedTickets += ticketsToAdd;
                logger.info("Added {} tickets. Pool amount: {}, Total released: {}", 
                    ticketsToAdd, poolTicketAmount, totalReleasedTickets);

                TicketUpdate update = new TicketUpdate("ADD", "SYSTEM", "TicketPool", 
                    ticketsToAdd, poolTicketAmount);
                ticketUpdateService.sendTicketUpdate(update);

                return ticketsToAdd;
            } else {
                int availableSpace = maxTicketCapacity - poolTicketAmount;
                if (availableSpace > 0) {
                    poolTicketAmount += availableSpace;
                    totalReleasedTickets += availableSpace;
                    logger.warn("Cannot add {} tickets. Added only {} to reach max capacity ({}).", tickets, availableSpace, maxTicketCapacity);

                    // Send WebSocket update
                    TicketUpdate update = new TicketUpdate("ADD_PARTIAL", "SYSTEM", "TicketPool", availableSpace, poolTicketAmount);
                    ticketUpdateService.sendTicketUpdate(update);

                    return availableSpace;
                }
                logger.warn("Cannot add {} tickets. Max capacity reached ({}).", tickets, maxTicketCapacity);

                // Send WebSocket update
                TicketUpdate update = new TicketUpdate("ADD_FAILED", "SYSTEM", "TicketPool", tickets, poolTicketAmount);
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

            if (tickets <= poolTicketAmount) {
                poolTicketAmount -= tickets;
                logger.info("Removed {} tickets. Pool amount: {}, Total released: {}", 
                    tickets, poolTicketAmount, totalReleasedTickets);

                TicketUpdate update = new TicketUpdate("REMOVE", "SYSTEM", "TicketPool", 
                    tickets, poolTicketAmount);
                ticketUpdateService.sendTicketUpdate(update);

                return true;
            } else {
                logger.warn("Cannot remove {} tickets. Only {} available.", tickets, poolTicketAmount);

                // Send WebSocket update
                TicketUpdate update = new TicketUpdate("REMOVE_FAILED", "SYSTEM", "TicketPool", tickets, poolTicketAmount);
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

    public synchronized int getPoolTicketAmount() {
        return poolTicketAmount;
    }

    public synchronized int getTotalReleasedTickets() {
        return totalReleasedTickets;
    }

    public synchronized int getTotalSystemTickets() {
        return totalSystemTickets;
    }

    public synchronized void reset() {
        this.poolTicketAmount = 0;
        this.totalReleasedTickets = 0;
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

