import { Component } from '@angular/core';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component';
import { HttpClientModule } from '@angular/common/http';
import { ToastContainerComponent } from './components/toast-container/toast-container.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    ConfigurationFormComponent,
    HttpClientModule,
    ToastContainerComponent,
    
  ],
})
export class AppComponent {
  title = 'frontend';
}
