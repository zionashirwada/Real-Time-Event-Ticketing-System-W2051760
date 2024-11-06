package lk.W2051760.ticketing_system_backend.controller;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketPoolController {

    @Autowired
    private TicketPool ticketPool;

    @GetMapping("/ticket-pool-status")
    public ResponseEntity<?> getTicketPoolStatus() {
        return ResponseEntity.ok(new TicketPoolStatus(
            ticketPool.getPoolTicketAmount(),
            ticketPool.getMaxTicketCapacity(),
            ticketPool.getTotalReleasedTickets(),
            ticketPool.getTotalSystemTickets()
        ));
    }

    // Inner class to represent the status
    public static class TicketPoolStatus {
        private int poolTicketAmount;
        private int maxTicketCapacity;
        private int totalReleasedTickets;
        private int totalSystemTickets;

        public TicketPoolStatus(int poolTicketAmount, int maxTicketCapacity, 
                              int totalReleasedTickets, int totalSystemTickets) {
            this.poolTicketAmount = poolTicketAmount;
            this.maxTicketCapacity = maxTicketCapacity;
            this.totalReleasedTickets = totalReleasedTickets;
            this.totalSystemTickets = totalSystemTickets;
        }

        public int getPoolTicketAmount() {
            return poolTicketAmount;
        }

        public int getMaxTicketCapacity() {
            return maxTicketCapacity;
        }

        public int getTotalReleasedTickets() {
            return totalReleasedTickets;
        }

        public int getTotalSystemTickets() {
            return totalSystemTickets;
        }
    }
}
