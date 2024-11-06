package lk.W2051760.ticketing_system_backend.model;

public class CountUpdate {
    private int vendorCount;
    private int customerCount;
    private int poolTicketAmount;
    private int totalReleasedTickets;

    public CountUpdate(int vendorCount, int customerCount, int poolTicketAmount, int totalReleasedTickets) {
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.poolTicketAmount = poolTicketAmount;
        this.totalReleasedTickets = totalReleasedTickets;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getPoolTicketAmount() {
        return poolTicketAmount;
    }

    public void setPoolTicketAmount(int poolTicketAmount) {
        this.poolTicketAmount = poolTicketAmount;
    }

    public int getTotalReleasedTickets() {
        return totalReleasedTickets;
    }

    public void setTotalReleasedTickets(int totalReleasedTickets) {
        this.totalReleasedTickets = totalReleasedTickets;
    }
}
