package lk.W2051760.ticketing_system_backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
