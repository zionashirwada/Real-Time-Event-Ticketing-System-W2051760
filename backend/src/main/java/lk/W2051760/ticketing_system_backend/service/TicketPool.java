package lk.W2051760.ticketing_system_backend.service;

public class TicketPool {

    private int totalTickets;
    private int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity) {
        this.totalTickets = 0;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(int tickets) {
        if (totalTickets + tickets <= maxTicketCapacity) {
            totalTickets += tickets;
            System.out.println("Added " + tickets + " tickets. Total tickets: " + totalTickets);
        } else {
            System.out.println("Cannot add " + tickets + " tickets. Max capacity reached.");
        }
    }

    public synchronized int getTotalTickets() {
        return totalTickets;
    }

    public synchronized int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}
