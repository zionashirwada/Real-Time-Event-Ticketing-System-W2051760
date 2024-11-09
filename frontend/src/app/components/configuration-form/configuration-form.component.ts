import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Configuration } from '../../models/configuration.model';
import { ConfigurationService } from '../../services/configuration.service';
import { ToastContainerComponent } from '../toast-container/toast-container.component';
import { ToastService } from '../../services/toast.service';
import { CommonModule } from '@angular/common';
import { WebSocketService } from '../../services/web-socket-service.service'

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [FormsModule, ToastContainerComponent,CommonModule],
})
export class ConfigurationFormComponent implements OnInit {
  configuration: Configuration = {
    totalSystemTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };
  systemStatus: string = 'STOPPED'

  constructor(
    private configService: ConfigurationService,
    private toast: ToastService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.loadConfiguration();

    // Subscribe to system status updates
    this.webSocketService.getSystemStatus().subscribe({
      next: (status: string) => {
        this.systemStatus = status
      },
      error: (error) => {
        console.error('Error getting system status:', error)
      }
    })
  }

  loadConfiguration(): void {
    this.configService.getConfiguration().subscribe({
      next: (config) => {
        this.configuration = config;
        console.log('Data Loaded from the Existing Json:', this.configuration);
      },
      error: (error) => {
        console.error('Error loading configuration:', error);
        this.configuration = {
          totalSystemTickets: 0,
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
    this.configService.saveConfiguration(this.configuration).subscribe(
      (response) => {
        console.log(response);
        console.log('Data entered to the Json:', this.configuration,response);
        this.toast.success(
          'Configuration has been saved successfully',
          'Success'
        );
        this.loadConfiguration();
      },
      (error) => {
        console.error('Error saving configuration:', error);
        this.toast.success(
          'Error saving configuration'+ error.message,
          'Error'
        );
      }
    );
  }

  isConfigDisabled(): boolean {
    return this.systemStatus === 'RUNNING' || this.systemStatus === 'PAUSED'
  }

}
