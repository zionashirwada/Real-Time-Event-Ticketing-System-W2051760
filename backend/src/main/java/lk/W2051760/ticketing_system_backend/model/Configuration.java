package lk.W2051760.ticketing_system_backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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


}
