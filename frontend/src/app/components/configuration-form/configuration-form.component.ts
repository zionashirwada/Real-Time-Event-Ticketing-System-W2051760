import { Component } from '@angular/core';
import { ConfigurationService } from '../../services/configuration.service';
import { Configuration } from '../../types/configuration.type';

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
})
export class ConfigurationFormComponent {
  config: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };

  constructor(private configurationService: ConfigurationService) {}

  onSubmit() {
    this.configurationService.setConfiguration(this.config).subscribe({
      next: (response) => {
        console.log('Configuration saved:', response);
        alert('Configuration saved successfully!');
      },
      error: (err) => {
        console.error('Error saving configuration:', err);
        alert('Error saving configuration.');
      },
    });
  }
}
