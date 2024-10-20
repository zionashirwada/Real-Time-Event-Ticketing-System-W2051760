// src/app/components/system-control/system-control.component.ts

import { Component, OnInit, OnDestroy } from '@angular/core';
import { SystemControlService } from '../../services/system-control.service';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../services/web-socket-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-system-control',
  templateUrl: './system-control.component.html',
  styleUrls: ['./system-control.component.css'],
  standalone: true,
  imports:[CommonModule]
})
export class SystemControlComponent implements OnInit, OnDestroy {
  systemStatus: string = 'Stopped';
  private systemStatusSubscription?: Subscription;

  constructor(
    private systemControlService: SystemControlService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {

    this.systemStatusSubscription = this.webSocketService.getSystemStatus().subscribe(
      (status: string) => {
        this.systemStatus = status;
      },
      (error) => {
        console.error('Error receiving system status:', error);
      }
    );


    this.systemControlService.getSystemStatus().subscribe(
      (status: string) => {
        this.systemStatus = status;
      },
      (error) => {
        console.error('Error fetching system status:', error);
      }
    );
  }

  startSystem(): void {
    this.systemControlService.startSystem().subscribe(
      (response: string) => {
        console.log(response);
      },
      (error) => {
        console.error('Error starting system:', error);
      }
    );
  }

  pauseSystem(): void {
    this.systemControlService.pauseSystem().subscribe(
      (response: string) => {
        console.log(response);
      },
      (error) => {
        console.error('Error pausing system:', error);
      }
    );
  }

  stopAndResetSystem(): void {
    this.systemControlService.stopAndResetSystem().subscribe(
      (response: string) => {
        console.log(response);
      },
      (error) => {
        console.error('Error stopping and resetting system:', error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.systemStatusSubscription) {
      this.systemStatusSubscription.unsubscribe();
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'RUNNING':
        return 'running';
      case 'PAUSED':
        return 'paused';
      case 'STOPPED':
        return 'stopped';
      default:
        return '';
    }
  }
}
