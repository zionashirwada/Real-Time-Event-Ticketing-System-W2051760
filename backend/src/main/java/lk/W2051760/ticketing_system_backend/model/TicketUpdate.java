package lk.W2051760.ticketing_system_backend.model;

public class TicketUpdate {
    private String action;        // "ADD", "REMOVE"
    private String entity;        //  "VENDOR", "CUSTOMER", "SYSTEM"
    private String name;          // Name of the vendor/customer
    private int tickets;          // Number of tickets added/removed
    private int totalTickets;     // Current total tickets in the pool

    // Constructors
    public TicketUpdate() {}

    public TicketUpdate(String action, String entity, String name, int tickets, int totalTickets) {
        this.action = action;
        this.entity = entity;
        this.name = name;
        this.tickets = tickets;
        this.totalTickets = totalTickets;
    }

    // Getters and Setters

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Override
    public String toString() {
        return "TicketUpdate{" +
                "action='" + action + '\'' +
                ", entity='" + entity + '\'' +
                ", name='" + name + '\'' +
                ", tickets=" + tickets +
                ", totalTickets=" + totalTickets +
                '}';
    }
}