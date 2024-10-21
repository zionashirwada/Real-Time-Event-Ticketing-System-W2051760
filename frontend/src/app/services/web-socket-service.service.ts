import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { TicketUpdate } from '../models/ticket-update.model';
import { CountUpdate } from '../models/count-update.model';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient?: Client;
  private ticketUpdateSubject: Subject<TicketUpdate> = new Subject<TicketUpdate>();
  private systemStatusSubject: Subject<string> = new Subject<string>();
  private countUpdateSubject: Subject<CountUpdate> = new Subject<CountUpdate>();


  constructor() {
    this.connect();
  }

  connect() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws-ticketing'),
      reconnectDelay: 5000,
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log('Connected to WebSocket via SockJS');

        // Subscribe to ticket updates
        this.stompClient?.subscribe('/topic/ticket-updates', (message: IMessage) => {
          if (message.body) {
            const ticketUpdate: TicketUpdate = JSON.parse(message.body);
            this.ticketUpdateSubject.next(ticketUpdate);
          }
        });

        // Subscribe to system status updates
        this.stompClient?.subscribe('/topic/system-status', (message: IMessage) => {
          if (message.body) {
            const systemStatus: string = message.body;
            console.log('Received System Status:', systemStatus);
            this.systemStatusSubject.next(systemStatus);
          }
        });

        // Subscribe to count updates (Vendor/Consumer)
        this.stompClient?.subscribe('/topic/count-updates', (message: IMessage) => {
          if (message.body) {
            const countUpdate: CountUpdate = JSON.parse(message.body);
            this.countUpdateSubject.next(countUpdate);
          }
        });

      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    this.stompClient.activate();
  }

  getTicketUpdates(): Observable<TicketUpdate> {
    return this.ticketUpdateSubject.asObservable();
  }

  getSystemStatus(): Observable<string> {
    return this.systemStatusSubject.asObservable();
  }
  getCountUpdates(): Observable<CountUpdate> {
    return this.countUpdateSubject.asObservable();
  }
  disconnect() {
    if (this.stompClient?.active) {
      this.stompClient.deactivate();
      console.log('Disconnected from WebSocket');
    }
  }
}
