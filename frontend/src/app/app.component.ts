import { Component } from '@angular/core';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    ConfigurationFormComponent,
    RouterModule,],
})

export class AppComponent {
  title = 'Ticket System - W2051760';
}
