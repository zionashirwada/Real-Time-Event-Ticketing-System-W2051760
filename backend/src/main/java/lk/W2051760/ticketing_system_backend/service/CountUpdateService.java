package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.CountUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CountUpdateService {

    private static final Logger logger = LogManager.getLogger(CountUpdateService.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final TicketPool ticketPool;

    // Store the current counts
    private int currentVendorCount = 0;
    private int currentCustomerCount =0;

    public CountUpdateService(SimpMessagingTemplate messagingTemplate, TicketPool ticketPool) {
        this.messagingTemplate = messagingTemplate;
        this.ticketPool = ticketPool;
    }

    public synchronized void updateVendorCount(int vendorCount) {
        this.currentVendorCount = vendorCount;
        sendCountUpdate();
    }

    public synchronized void updateCustomerCount(int customerCount) {
        this.currentCustomerCount = customerCount;
        sendCountUpdate();
    }

    private void sendCountUpdate() {
        CountUpdate countUpdate = new CountUpdate(
            currentVendorCount,
            currentCustomerCount,
            ticketPool.getPoolTicketAmount(),
            ticketPool.getTotalReleasedTickets()
        );
        messagingTemplate.convertAndSend("/topic/count-updates", countUpdate);
        logger.info("Broadcasted count update: Vendors={}, Customers={}, PoolTickets={}, TotalReleased={}",
            currentVendorCount, currentCustomerCount, countUpdate.getPoolTicketAmount(), countUpdate.getTotalReleasedTickets());
    }
}
