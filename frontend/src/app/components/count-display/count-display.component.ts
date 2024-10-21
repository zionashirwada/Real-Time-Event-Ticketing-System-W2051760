import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WebSocketService } from '../../services/web-socket-service.service';
import { ManagementService } from '../../services/management.service';
import { CountUpdate } from '../../models/count-update.model';

@Component({
  selector: 'app-count-display',
  templateUrl: './count-display.component.html',
  styleUrls: ['./count-display.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class CountDisplayComponent implements OnInit {
  vendorCount: number = 0;
  customerCount: number = 0;

  constructor(
    private webSocketService: WebSocketService,
    private managementService: ManagementService
  ) {}

  ngOnInit(): void {
    // Subscribe to count updates
    this.webSocketService.getCountUpdates().subscribe((update: CountUpdate) => {
      this.vendorCount = update.vendorCount;
      this.customerCount = update.customerCount;
    });
  }

  // Methods to add/remove vendors and customers
  addVendor() {
    this.managementService.addVendor().subscribe({
      next: (response) => console.log('Vendor added:', response),
      error: (error) => console.error('Error adding vendor:', error),
    });
  }

  removeVendor() {
    this.managementService.removeVendor().subscribe({
      next: (response) => console.log('Vendor removed:', response),
      error: (error) => console.error('Error removing vendor:', error),
    });
  }

  addCustomer() {
    this.managementService.addCustomer().subscribe({
      next: (response) => console.log('Customer added:', response),
      error: (error) => console.error('Error adding customer:', error),
    });
  }

  removeCustomer() {
    this.managementService.removeCustomer().subscribe({
      next: (response) => console.log('Customer removed:', response),
      error: (error) => console.error('Error removing customer:', error),
    });
  }
}
