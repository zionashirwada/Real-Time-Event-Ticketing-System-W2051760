import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ManagementService {
  private apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {}

  // Vendor management methods
  addVendor(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vendors/add`, {}, { responseType: 'text' });
  }

  removeVendor(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vendors/remove`, {}, { responseType: 'text' });
  }

  // Customer management methods
  addCustomer(): Observable<any> {
    return this.http.post(`${this.apiUrl}/customers/add`, {}, { responseType: 'text' });
  }

  removeCustomer(): Observable<any> {
    return this.http.post(`${this.apiUrl}/customers/remove`, {}, { responseType: 'text' });
  }

  // VIP Customer management methods
  addVIPCustomer(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vip-customers/add`, {}, { responseType: 'text' })
  }

  removeVIPCustomer(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vip-customers/remove`, {}, { responseType: 'text' })
  }

  // Vendor Thread management methods
  pauseVendorThreads(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vendors/pause`, {}, { responseType: 'text' })
  }

  resumeVendorThreads(): Observable<any> {
    return this.http.post(`${this.apiUrl}/vendors/resume`, {}, { responseType: 'text' })
  }

  // Customer Thread management methods
  pauseCustomerThreads(): Observable<any> {
    return this.http.post(`${this.apiUrl}/customers/pause`, {}, { responseType: 'text' })
  }

  resumeCustomerThreads(): Observable<any> {
    return this.http.post(`${this.apiUrl}/customers/resume`, {}, { responseType: 'text' })
  }
}
