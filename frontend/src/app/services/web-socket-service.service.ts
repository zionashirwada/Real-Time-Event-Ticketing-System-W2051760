// src/app/services/websocket.service.ts

import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client'; // Correct default import
import { TicketUpdate } from '../models/ticket-update.model';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient?: Client;
  private ticketUpdateSubject: Subject<TicketUpdate> = new Subject<TicketUpdate>();

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
        this.stompClient?.subscribe('/topic/ticket-updates', (message: IMessage) => {
          if (message.body) {
            const ticketUpdate: TicketUpdate = JSON.parse(message.body);
            console.log('Received TicketUpdate:', ticketUpdate); 
            this.ticketUpdateSubject.next(ticketUpdate);
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

  disconnect() {
    if (this.stompClient?.active) {
      this.stompClient.deactivate();
      console.log('Disconnected from WebSocket');
    }
  }
}
