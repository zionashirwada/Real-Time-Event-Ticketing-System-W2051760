import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Configuration } from '../../models/configuration.model';
import { ConfigurationService } from '../../services/configuration.service';
import { ToastContainerComponent } from '../toast-container/toast-container.component';
import { ToastService } from '../../services/toast.service';
import { CommonModule } from '@angular/common';

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

}
