package lk.W2051760.ticketing_system_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TicketPool {
    private static final Logger logger = LogManager.getLogger(TicketPool.class);
    private int totalTickets;
    private int maxTicketCapacity;

    public TicketPool() {
        // Default constructor required by Spring
    }

    public void initialize(int maxTicketCapacity) {
        this.totalTickets = 0;
        this.maxTicketCapacity = maxTicketCapacity;
        logger.info("TicketPool initialized with max capacity: {}", maxTicketCapacity);
    }
// Sync methods - add //reove
public synchronized void addTickets(int tickets) {
    try {
        if (tickets <= 0) {
            throw new IllegalArgumentException("Number of tickets to add must be positive.");
        }

        if (totalTickets + tickets <= maxTicketCapacity) {
            totalTickets += tickets;
            logger.info("Added {} tickets. Total tickets: {}", tickets, totalTickets);
        } else {
            logger.warn("Cannot add {} tickets. Max capacity reached ({}).", tickets, maxTicketCapacity);
        }
    } catch (IllegalArgumentException e) {
        logger.error("Invalid argument in addTickets: {}", e.getMessage(), e);
    } catch (Exception e) {
        logger.error("Unexpected error in addTickets: {}", e.getMessage(), e);
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
                return true;
            } else {
                logger.warn("Cannot remove {} tickets. Only {} available.", tickets, totalTickets);
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

    public synchronized int getMaxTicketCapacity() {
        try {
            return maxTicketCapacity;
        } catch (Exception e) {
            logger.error("Error while getting max ticket capacity: {}", e.getMessage(), e);
            return -1;
        }
    }
}
