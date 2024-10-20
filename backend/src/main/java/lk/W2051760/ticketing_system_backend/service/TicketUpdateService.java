package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketUpdateService {

    private static final Logger logger = LogManager.getLogger(TicketUpdateService.class);

    private final SimpMessagingTemplate messagingTemplate;

    public TicketUpdateService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendTicketUpdate(TicketUpdate ticketUpdate) {
        try {
            messagingTemplate.convertAndSend("/topic/ticket-updates", ticketUpdate);
            logger.info("Broadcasted ticket update: {}", ticketUpdate);
        } catch (Exception e) {
            logger.error("Failed to send ticket update: {}", e.getMessage(), e);
        }
    }
}
