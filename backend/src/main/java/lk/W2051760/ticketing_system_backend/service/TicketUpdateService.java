/**
 * TicketUpdateService.java
 * 
 * This file contains the TicketUpdateService class, which is responsible for 
 * broadcasting ticket updates to clients via WebSocket messaging.
 */
package lk.W2051760.ticketing_system_backend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;

@Service
public class TicketUpdateService {

    private static final Logger logger = LogManager.getLogger(TicketUpdateService.class);

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructor for TicketUpdateService.
     * 
     * @param messagingTemplate the SimpMessagingTemplate used for sending messages.
     */
    public TicketUpdateService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a ticket update to the specified WebSocket topic.
     * 
     * @param ticketUpdate the TicketUpdate object containing the update details to
     *                     broadcast.
     */
    public void sendTicketUpdate(TicketUpdate ticketUpdate) {
        try {
            messagingTemplate.convertAndSend("/topic/ticket-updates", ticketUpdate);
            logger.info("Broadcasted ticket update: {}", ticketUpdate);
        } catch (Exception e) {
            logger.error("Failed to send ticket update: {}", e.getMessage(), e);
        }
    }
}
