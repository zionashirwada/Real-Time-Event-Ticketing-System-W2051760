package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;

public class Customer implements Runnable {

    private String customerName;
    private int ticketsToPurchase;
    private TicketPool ticketPool;
    private volatile boolean running;

    public Customer(String customerName, int ticketsToPurchase, TicketPool ticketPool) {
        this.customerName = customerName;
        this.ticketsToPurchase = ticketsToPurchase;
        this.ticketPool = ticketPool;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Attempt to purchase tickets
                boolean success = ticketPool.removeTickets(ticketsToPurchase);
                if (success) {
                    System.out.println(customerName + " successfully purchased " + ticketsToPurchase + " tickets.");
                } else {
                    System.out.println(customerName + " failed to purchase " + ticketsToPurchase + " tickets. Not enough tickets available.");
                }
                // pause amount
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(customerName + " was interrupted.");
            }
        }
    }

    public void stop() {
        running = false;
    }
}
