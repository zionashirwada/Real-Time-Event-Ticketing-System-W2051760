package lk.W2051760.ticketing_system_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPool {

    private int totalTickets;
    private int maxTicketCapacity;

    public TicketPool() {
        // Default constructor required by Spring
    }

    public void initialize(int maxTicketCapacity) {
        this.totalTickets = 0;
        this.maxTicketCapacity = maxTicketCapacity;
    }
// Sync methods - add //reove
    public synchronized void addTickets(int tickets) {
        if (totalTickets + tickets <= maxTicketCapacity) {
            totalTickets += tickets;
            System.out.println("Added " + tickets + " tickets. Total tickets: " + totalTickets);
        } else {
            System.out.println("Cannot add " + tickets + " tickets. Max capacity reached.");
        }
    }

    public synchronized boolean removeTickets(int tickets) {
        if (tickets <= totalTickets) {
            totalTickets -= tickets;
            System.out.println("Removed " + tickets + " tickets. Total tickets: " + totalTickets);
            return true;
        } else {
            System.out.println("Cannot remove " + tickets + " tickets. Only " + totalTickets + " available.");
            return false;
        }
    }

    public synchronized int getTotalTickets() {
        return totalTickets;
    }

    public synchronized int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}
