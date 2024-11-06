package lk.W2051760.ticketing_system_backend.model;

public class TransactionLog {
    private String action;       // "ADD" or "REMOVE"
    private String entity;       // "VENDOR" or "CUSTOMER"
    private String name;         // Name of the vendor/customer
    private int tickets;         // Number of tickets added/removed
    private int currentPoolAmount;

    // Default constructor
    public TransactionLog() {}

    // Parameterized constructor
    public TransactionLog(String action, String entity, String name, int tickets, int currentPoolAmount) {
        this.action = action;
        this.entity = entity;
        this.name = name;
        this.tickets = tickets;
        this.currentPoolAmount = currentPoolAmount;
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

    public int getCurrentPoolAmount() {
        return currentPoolAmount;
    }

    public void setCurrentPoolAmount(int currentPoolAmount) {
        this.currentPoolAmount = currentPoolAmount;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "action='" + action + '\'' +
                ", entity='" + entity + '\'' +
                ", name='" + name + '\'' +
                ", tickets=" + tickets +
                ", currentPoolAmount=" + currentPoolAmount +
                '}';
    }
}
