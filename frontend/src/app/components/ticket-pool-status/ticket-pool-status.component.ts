import { Component, OnInit, OnDestroy } from '@angular/core';
import { TicketService, TicketPoolStatus, } from '../../services/ticket.service';
import { Subscription } from 'rxjs';
import { TicketUpdate } from '../../models/ticket-update.model';
import { WebSocketService } from '../../services/web-socket-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ticket-pool-status',
  templateUrl: './ticket-pool-status.component.html',
  styleUrls: ['./ticket-pool-status.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class TicketPoolStatusComponent implements OnInit, OnDestroy {
  totalTickets: number = 0;
  maxTicketCapacity: number = 0;
  errorMessage: string = '';
  private subscription?: Subscription;
  private websocketSubscription?: Subscription;

  constructor(private ticketService: TicketService, private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    // Initial load
    this.ticketService.getTicketPoolStatus().subscribe(
      (status: TicketPoolStatus) => {
        this.totalTickets = status.totalTickets;
        this.maxTicketCapacity = status.maxTicketCapacity;
      },
      (error) => {
        console.error('Error loading ticket pool status:', error);
        this.errorMessage = error.message;
      }
    );

    // Subscribe to WebSocket updates
    this.websocketSubscription = this.webSocketService.getTicketUpdates().subscribe(
      (update: TicketUpdate) => {
        // Update local state based on the action
        if (update.action === 'ADD' || update.action === 'ADD_PARTIAL') {
          this.totalTickets = update.totalTickets;
        } else if (update.action === 'REMOVE') {
          this.totalTickets = update.totalTickets;
        }
      },
      (error) => {
        console.error('WebSocket error:', error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    if (this.websocketSubscription) {
      this.websocketSubscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
