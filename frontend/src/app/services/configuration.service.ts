import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Configuration } from '../types/configuration.type';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class ConfigurationService {
  private apiUrl = `${environment.apiBaseUrl}/api/configuration`;

  constructor(private http: HttpClient) {}

  getConfiguration(): Observable<Configuration> {
    return this.http.get<Configuration>(this.apiUrl);
  }

  setConfiguration(config: Configuration): Observable<Configuration> {
    return this.http.post<Configuration>(this.apiUrl, config);
  }
}
