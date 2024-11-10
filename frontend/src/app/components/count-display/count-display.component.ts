import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WebSocketService } from '../../services/web-socket-service.service';
import { ManagementService } from '../../services/management.service';
import { CountUpdate } from '../../models/count-update.model';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { bootstrapDashLg, bootstrapPeople, bootstrapPlusLg, bootstrapShop, bootstrapStars } from '@ng-icons/bootstrap-icons';

@Component({
  selector: 'app-count-display',
  templateUrl: './count-display.component.html',
  styleUrls: ['./count-display.component.css'],
  standalone: true,
  imports: [CommonModule,
    NgIconComponent,
  ],
  providers: [provideIcons({ bootstrapPeople,bootstrapShop,bootstrapStars,bootstrapPlusLg,bootstrapDashLg})],
})
export class CountDisplayComponent implements OnInit {
  customerCount = 0
  vendorCount = 0
  vipCustomerCount = 0

  constructor(
    private webSocketService: WebSocketService,
    private managementService: ManagementService
  ) {}

  ngOnInit(): void {
    this.webSocketService.getCountUpdates().subscribe((update) => {
      this.customerCount = update.customerCount
      this.vendorCount = update.vendorCount
      this.vipCustomerCount = update.vipCustomerCount
    })
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

  addVIPCustomer() {
    this.managementService.addVIPCustomer().subscribe({
      next: (response) => console.log('VIP Customer added:', response),
      error: (error) => console.error('Error adding VIP customer:', error),
    })
  }

  removeVIPCustomer() {
    this.managementService.removeVIPCustomer().subscribe({
      next: (response) => console.log('VIP Customer removed:', response),
      error: (error) => console.error('Error removing VIP customer:', error),
    })
  }

  pauseVendorThreads() {
    this.managementService.pauseVendorThreads().subscribe({
      next: (response) => console.log('Vendor threads paused:', response),
      error: (error) => console.error('Error pausing vendor threads:', error),
    })
  }

  resumeVendorThreads() {
    this.managementService.resumeVendorThreads().subscribe({
      next: (response) => console.log('Vendor threads resumed:', response),
      error: (error) => console.error('Error resuming vendor threads:', error),
    })
  }

  pauseCustomerThreads() {
    this.managementService.pauseCustomerThreads().subscribe({
      next: (response) => console.log('Customer threads paused:', response),
      error: (error) => console.error('Error pausing customer threads:', error),
    })
  }

  resumeCustomerThreads() {
    this.managementService.resumeCustomerThreads().subscribe({
      next: (response) => console.log('Customer threads resumed:', response),
      error: (error) => console.error('Error resuming customer threads:', error),
    })
  }
}
