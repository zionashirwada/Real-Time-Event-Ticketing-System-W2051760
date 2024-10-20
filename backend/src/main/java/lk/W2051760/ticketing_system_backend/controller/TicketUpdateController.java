package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TicketUpdateController {

    private static final Logger logger = LogManager.getLogger(TicketUpdateController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public TicketUpdateController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a ticket update message to all subscribed clients.
     *
     * @param ticketUpdate The ticket update information.
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
