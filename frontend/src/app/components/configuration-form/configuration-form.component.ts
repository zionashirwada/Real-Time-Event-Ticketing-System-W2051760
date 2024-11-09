import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Configuration } from '../../models/configuration.model';
import { ConfigurationService } from '../../services/configuration.service';
import { ToastContainerComponent } from '../toast-container/toast-container.component';
import { ToastService } from '../../services/toast.service';
import { CommonModule } from '@angular/common';
import { WebSocketService } from '../../services/web-socket-service.service'
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [FormsModule, ToastContainerComponent,CommonModule],
})
export class ConfigurationFormComponent implements OnInit, OnDestroy {
  configuration: Configuration = {
    totalSystemTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  };
  systemStatus: string = 'NOT_CONFIGURED'
  isNotConfigured: boolean = true
  private configSubscription?: Subscription

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

    // Subscribe to configuration updates
    this.configSubscription = this.webSocketService.getConfigurationUpdates()
      .subscribe((config: Configuration) => {
        this.configuration = config
        this.isNotConfigured = false
      })
  }

  ngOnDestroy(): void {
    this.configSubscription?.unsubscribe()
  }

  loadConfiguration(): void {
    this.configService.getConfiguration().subscribe({
      next: (config) => {
        this.configuration = config
        this.isNotConfigured = false
        console.log('Data Loaded from the Existing Json:', this.configuration)
      },
      error: (error) => {
        console.error('Error loading configuration:', error)
        this.isNotConfigured = true
        this.configuration = {
          totalSystemTickets: 0,
          ticketReleaseRate: 0,
          customerRetrievalRate: 0,
          maxTicketCapacity: 0
        }
        if (error.status === 404) {
          this.toast.warning(
            'System is not configured. Please configure the system.',
            'Warning'
          )
        } else {
          this.toast.error(
            'Failed to load configuration. Please try again later.',
            'Error'
          )
        }
      }
    })
  }

  onSubmit(): void {
    this.configService.saveConfiguration(this.configuration).subscribe({
      next: (response) => {
        console.log('Configuration saved:', this.configuration)
        this.toast.success(
          'Configuration has been saved successfully',
          'Success'
        )

        // Reload configuration and reinitialize system
        this.configService.reloadSystem().subscribe({
          next: () => {
            // No need to call loadConfiguration() as we'll receive the update via WebSocket
            this.isNotConfigured = false
          },
          error: (error) => {
            console.error('Error reinitializing system:', error)
            this.toast.error(
              'Failed to reinitialize system. Please try again.',
              'Error'
            )
          }
        })
      },
      error: (error) => {
        console.error('Error saving configuration:', error)
        this.toast.error(
          'Error saving configuration: ' + error.message,
          'Error'
        )
      }
    })
  }

  isConfigDisabled(): boolean {
    return this.systemStatus === 'RUNNING' || this.systemStatus === 'PAUSED'
  }

}
