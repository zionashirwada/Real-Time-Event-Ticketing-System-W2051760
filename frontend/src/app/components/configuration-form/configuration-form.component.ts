import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../../services/configuration.service';
import { Configuration } from '../../types/configuration.type';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [],
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
})
export class ConfigurationFormComponent implements OnInit {
  config: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };

  constructor(private configurationService: ConfigurationService) {}

  ngOnInit() {
    // Fetch the saved configs
    this.configurationService.getConfiguration().subscribe({
      next: (data) => {
        if (data) {
          this.config = data; 
        }
      },
      error: (err) => {
        console.error('Error fetching configuration:', err);
      },
    });
  }

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
