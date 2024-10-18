import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent {
  config = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };

  onSubmit() {
    console.log('Configuration submitted:', this.config);
    alert('Configuration saved successfully!');
  }
}
