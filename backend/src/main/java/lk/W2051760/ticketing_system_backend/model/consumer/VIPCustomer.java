package lk.W2051760.ticketing_system_backend.model.consumer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;

public class VIPCustomer extends Customer {
    private int loyaltyPoints;

    public VIPCustomer(int id, String name, int ticketsToPurchase, TicketPool ticketPool, TicketUpdateService ticketUpdateService, TransactionLogService transactionLogService) {
        super(id, name, ticketsToPurchase, ticketPool, ticketUpdateService, transactionLogService);
    }

}
