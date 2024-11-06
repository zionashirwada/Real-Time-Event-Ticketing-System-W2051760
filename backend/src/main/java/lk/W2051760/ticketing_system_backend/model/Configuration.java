package lk.W2051760.ticketing_system_backend.model;

public class Configuration {
    private int totalSystemTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Constructor
    public Configuration() {
    }
    public Configuration(int totalSystemTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalSystemTickets = totalSystemTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Getters and Setters
    public int getTotalSystemTickets() {
        return totalSystemTickets;
    }

    public void setTotalSystemTickets(int totalSystemTickets) {
        this.totalSystemTickets = totalSystemTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
