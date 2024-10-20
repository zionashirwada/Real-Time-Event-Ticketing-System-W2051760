import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SystemControlService {
  private baseUrl = 'http://localhost:8080/api/system';

  constructor(private http: HttpClient) {}

  startSystem(): Observable<string> {
    return this.http.post(this.baseUrl + '/start', {}, { responseType: 'text' });
  }

  pauseSystem(): Observable<string> {
    return this.http.post(this.baseUrl + '/pause', {}, { responseType: 'text' });
  }

  stopAndResetSystem(): Observable<string> {
    return this.http.post(this.baseUrl + '/stop-reset', {}, { responseType: 'text' });
  }

  getSystemStatus(): Observable<string> {
    return this.http.get(this.baseUrl + '/status', { responseType: 'text' });
  }
}
