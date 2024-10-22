import { Component } from '@angular/core';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component';
import { HttpClientModule } from '@angular/common/http';
import { ToastContainerComponent } from './components/toast-container/toast-container.component';
import { TicketPoolStatusComponent } from './components/ticket-pool-status/ticket-pool-status.component';
import { SystemControlComponent } from './components/system-control/system-control.component';
import { CountDisplayComponent } from './components/count-display/count-display.component';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { bootstrapBarChartLine, bootstrapTicketPerforated } from '@ng-icons/bootstrap-icons';
import { TransactionLogComponent } from './components/transaction-log/transaction-log.component';
import { LineChartComponent } from './components/line-chart/line-chart.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    ConfigurationFormComponent,
    HttpClientModule,
    ToastContainerComponent,
    TicketPoolStatusComponent,
    SystemControlComponent,
    CountDisplayComponent,
    NgIconComponent,
    TransactionLogComponent,
    LineChartComponent
  ],
  providers: [provideIcons({ bootstrapTicketPerforated,bootstrapBarChartLine })],
})
export class AppComponent {
  title = 'frontend';
}
