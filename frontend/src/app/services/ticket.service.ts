import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface TicketPoolStatus {
  totalTickets: number;
  maxTicketCapacity: number;
}

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private apiUrl = `${environment.apiUrl}/ticket-pool-status`;

  constructor(private http: HttpClient) {}

  getTicketPoolStatus(): Observable<TicketPoolStatus> {
    return this.http.get<TicketPoolStatus>(`${this.apiUrl}`);
  }
}
