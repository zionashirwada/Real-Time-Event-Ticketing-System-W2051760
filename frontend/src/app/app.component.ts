import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ConfigurationFormComponent],
  template: `<app-configuration-form></app-configuration-form>`,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
}
