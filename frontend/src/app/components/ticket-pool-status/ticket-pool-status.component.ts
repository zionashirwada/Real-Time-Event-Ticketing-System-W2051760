import { Component, OnInit, OnDestroy } from '@angular/core';
import { TicketService, TicketPoolStatus, } from '../../services/ticket.service';
import { Subscription } from 'rxjs';
import { TicketUpdate } from '../../models/ticket-update.model';
import { WebSocketService } from '../../services/web-socket-service.service';
import { CommonModule } from '@angular/common';
import { SystemControlService } from '../../services/system-control.service';

@Component({
  selector: 'app-ticket-pool-status',
  templateUrl: './ticket-pool-status.component.html',
  styleUrls: ['./ticket-pool-status.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class TicketPoolStatusComponent implements OnInit, OnDestroy {
  poolTicketAmount: number = 0;
  maxTicketCapacity: number = 0;
  totalReleasedTickets: number = 0;
  totalSystemTickets: number = 0;
  errorMessage: string = '';
  private subscription?: Subscription;
  private websocketSubscription?: Subscription;

  constructor(private ticketService: TicketService, private webSocketService: WebSocketService,private systemControlService: SystemControlService) {}

  ngOnInit(): void {
    // Initial load
    this.ticketService.getTicketPoolStatus().subscribe(
      (status: TicketPoolStatus) => {
        this.updateStatus(status);
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
          this.poolTicketAmount = update.currentPoolAmount;
          this.totalReleasedTickets += update.tickets;
        } else if (update.action === 'REMOVE') {
          this.poolTicketAmount = update.currentPoolAmount;
        } else if (update.action === 'RESET') {
          // Handle system reset
          this.poolTicketAmount = 0;
          this.totalReleasedTickets = 0;
        }
        this.checkTicketCapacity();
      },
      (error) => {
        console.error('WebSocket error:', error);
      }
    );


  }

  // Helper method to update all status values
  private updateStatus(status: TicketPoolStatus) {
    this.poolTicketAmount = status.poolTicketAmount;
    this.maxTicketCapacity = status.maxTicketCapacity;
    this.totalReleasedTickets = status.totalReleasedTickets;
    this.totalSystemTickets = status.totalSystemTickets;
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

  // Method to check ticket capacity
  checkTicketCapacity(): void {
    if (this.totalReleasedTickets >= this.totalSystemTickets) {
      this.errorMessage = 'All the ' + this.totalReleasedTickets + ' tickets have been released. Maximum capacity reached!';
      this.systemControlService.pauseSystem().subscribe(
        (response: string) => {
          console.log('System paused due to capacity reached:', response);
        },
        (error) => {
          console.error('Error pausing system:', error);
        }
      );
    }
  }

}
