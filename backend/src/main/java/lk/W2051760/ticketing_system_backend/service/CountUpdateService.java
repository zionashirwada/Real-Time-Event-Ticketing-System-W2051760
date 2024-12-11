/**
 * CountUpdateService.java
 * 
 * This file contains the CountUpdateService class, which is responsible for 
 * broadcasting updates about the current counts of vendors, customers, VIP customers, 
 * and ticket information. It integrates with a messaging system to provide real-time updates.
 */
package lk.W2051760.ticketing_system_backend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lk.W2051760.ticketing_system_backend.model.CountUpdate;

@Service
public class CountUpdateService {

    private static final Logger logger = LogManager.getLogger(CountUpdateService.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final TicketPool ticketPool;

    // Store the current counts
    private int currentVendorCount = 0;
    private int currentCustomerCount = 0;
    private int currentVIPCustomerCount = 0;

    /**
     * Constructs a CountUpdateService with the specified messaging template and
     * ticket pool.
     *
     * @param messagingTemplate the SimpMessagingTemplate used for sending messages
     * @param ticketPool        the TicketPool used to retrieve ticket information
     */
    public CountUpdateService(SimpMessagingTemplate messagingTemplate, TicketPool ticketPool) {
        this.messagingTemplate = messagingTemplate;
        this.ticketPool = ticketPool;
    }

    /**
     * Updates the current vendor count and sends a count update message.
     *
     * @param vendorCount the new vendor count
     */
    public synchronized void updateVendorCount(int vendorCount) {
        this.currentVendorCount = vendorCount;
        sendCountUpdate();
    }

    /**
     * Updates the current customer count and sends a count update message.
     *
     * @param customerCount the new customer count
     */
    public synchronized void updateCustomerCount(int customerCount) {
        this.currentCustomerCount = customerCount;
        sendCountUpdate();
    }

    /**
     * Updates the current VIP customer count and sends a count update message.
     *
     * @param vipCustomerCount the new VIP customer count
     */
    public synchronized void updateVIPCustomerCount(int vipCustomerCount) {
        this.currentVIPCustomerCount = vipCustomerCount;
        sendCountUpdate();
    }

    /**
     * Sends a count update message containing the current counts and ticket
     * information.
     * 
     * This method retrieves the ticket information from the TicketPool, creates a
     * CountUpdate object, and broadcasts it to the "/topic/count-updates" topic. It
     * also logs the broadcasted data for monitoring and debugging purposes.
     */
    private void sendCountUpdate() {
        CountUpdate countUpdate = new CountUpdate(
                currentVendorCount,
                currentCustomerCount,
                currentVIPCustomerCount,
                ticketPool.getPoolTicketAmount(),
                ticketPool.getTotalReleasedTickets());
        messagingTemplate.convertAndSend("/topic/count-updates", countUpdate);
        logger.info(
                "Broadcasted count update: Vendors={}, Customers={}, VIPCustomers={}, PoolTickets={}, TotalReleased={}",
                currentVendorCount, currentCustomerCount, currentVIPCustomerCount,
                countUpdate.getPoolTicketAmount(), countUpdate.getTotalReleasedTickets());
    }
}
