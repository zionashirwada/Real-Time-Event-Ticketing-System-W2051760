package lk.W2051760.ticketing_system_backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountUpdate {
    private int vendorCount;
    private int customerCount;
    private int vipCustomerCount;
    private int poolTicketAmount;
    private int totalReleasedTickets;

    public CountUpdate(int vendorCount, int customerCount, int vipCustomerCount,
                      int poolTicketAmount, int totalReleasedTickets) {
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.vipCustomerCount = vipCustomerCount;
        this.poolTicketAmount = poolTicketAmount;
        this.totalReleasedTickets = totalReleasedTickets;
    }


}
