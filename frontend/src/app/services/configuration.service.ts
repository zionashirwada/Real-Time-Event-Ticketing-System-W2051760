import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Configuration } from '../types/configuration.type';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class ConfigurationService {
  private apiUrl = `${environment.apiBaseUrl}/api`;

  constructor(private http: HttpClient) {}

  saveConfiguration(config: Configuration): Observable<string> {
    return this.http.post(`${this.apiUrl}/configuration`, config, { responseType: 'text' });
  }

  getConfiguration(): Observable<Configuration> {
    return this.http.get<Configuration>(`${this.apiUrl}/configuration`);
  }
}
