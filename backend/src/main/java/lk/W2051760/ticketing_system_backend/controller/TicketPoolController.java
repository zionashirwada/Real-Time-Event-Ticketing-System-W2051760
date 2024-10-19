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
        int totalTickets = ticketPool.getTotalTickets();
        int maxTicketCapacity = ticketPool.getMaxTicketCapacity();

        return ResponseEntity.ok(new TicketPoolStatus(totalTickets, maxTicketCapacity));
    }

    // Inner class to represent the status
    public static class TicketPoolStatus {
        private int totalTickets;
        private int maxTicketCapacity;

        public TicketPoolStatus(int totalTickets, int maxTicketCapacity) {
            this.totalTickets = totalTickets;
            this.maxTicketCapacity = maxTicketCapacity;
        }

        public int getTotalTickets() {
            return totalTickets;
        }

        public int getMaxTicketCapacity() {
            return maxTicketCapacity;
        }
    }
}
