import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Configuration } from '../../models/configuration.model';
import { ConfigurationService } from '../../services/configuration.service';
import { ToastContainerComponent } from '../toast-container/toast-container.component';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [FormsModule, ToastContainerComponent],
})
export class ConfigurationFormComponent implements OnInit {
  configuration: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };

  constructor(
    private configService: ConfigurationService,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    this.loadConfiguration();
  }

  loadConfiguration(): void {
    this.configService.getConfiguration().subscribe({
      next: (config) => {
        this.configuration = config;
      },
      error: (error) => {
        console.error('Error loading configuration:', error);
        this.configuration = {
          totalTickets: 0,
          ticketReleaseRate: 0,
          customerRetrievalRate: 0,
          maxTicketCapacity: 0
        };
        this.toast.error(
          'Failed to load configuration. Please try again later.',
          'Error'
        );
      }
    });
  }

  onSubmit(): void {
    this.configService.saveConfiguration(this.configuration).subscribe({
      next: () => {
        this.toast.success(
          'Configuration has been saved successfully',
          'Success'
        );
      },
      error: (error) => {
        console.error('Error saving configuration:', error);
        this.toast.error(
          'Failed to save configuration. Please try again.',
          'Error'
        );
      }
    });
  }
}
