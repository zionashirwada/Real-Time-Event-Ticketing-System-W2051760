// src/app/services/websocket.service.ts

import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { TicketUpdate } from '../models/ticket-update.model';


@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient?: Stomp.Client;
  private ticketUpdateSubject: Subject<TicketUpdate> = new Subject<TicketUpdate>();

  constructor() {
    this.connect();
  }

  /**
   * Establishes a WebSocket connection and subscribes to the ticket updates topic.
   */
  connect() {
     // backend URL
    const socket = new SockJS('http://localhost:8080/ws-ticketing');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      console.log('Connected to WebSocket');
      this.stompClient?.subscribe('/topic/ticket-updates', (message) => {
        if (message.body) {
          const ticketUpdate: TicketUpdate = JSON.parse(message.body);
          this.ticketUpdateSubject.next(ticketUpdate);
        }
      });
    }, (error) => {
      console.error('WebSocket connection error:', error);
    });
  }

  /**
   * Returns an observable to subscribe to ticket updates.
   */
  getTicketUpdates(): Observable<TicketUpdate> {
    return this.ticketUpdateSubject.asObservable();
  }

  /**
   * Disconnects the WebSocket connection.
   */
  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient?.disconnect(() => {
        console.log('Disconnected from WebSocket');
      });
    }
  }
}
