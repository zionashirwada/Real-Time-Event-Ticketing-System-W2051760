import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-pool-status',
  standalone: true,
  imports: [],
  templateUrl: './ticket-pool-status.component.html',
  styleUrl: './ticket-pool-status.component.css'
})
export class TicketPoolStatusComponent implements OnInit {
  totalTickets: number = 0;
  maxTicketCapacity: number = 0;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.loadTicketPoolStatus();

    // Refresh every 2 seconds
    setInterval(() => {
      this.loadTicketPoolStatus();
    }, 2000);
  }


  loadTicketPoolStatus(): void {
    this.ticketService.getTicketPoolStatus().subscribe(
      (status) => {
        this.totalTickets = status.totalTickets;
        this.maxTicketCapacity = status.maxTicketCapacity;
      },
      (error) => {
        console.error('Error loading ticket pool status:', error);
      }
    );
  }
}
