import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import {
  ApexChart,
  ApexAxisChartSeries,
  ApexXAxis,
  ApexTitleSubtitle,
  NgApexchartsModule,
  ApexStroke,
  ApexDataLabels,
  ApexYAxis,
  ApexLegend,
} from 'ng-apexcharts';
import { WebSocketService } from '../../services/web-socket-service.service';
import { TicketUpdate } from '../../models/ticket-update.model';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  title: ApexTitleSubtitle;
  stroke: ApexStroke;
  dataLabels: ApexDataLabels;
  yaxis: ApexYAxis;
  legend: ApexLegend;
};

@Component({
  selector: 'app-line-chart',
  template: `
    <apx-chart
      [series]="chartOptions.series"
      [chart]="chartOptions.chart"
      [xaxis]="chartOptions.xaxis"
      [stroke]="chartOptions.stroke"
      [dataLabels]="chartOptions.dataLabels"
      [title]="chartOptions.title"
      [yaxis]="chartOptions.yaxis"
      [legend]="chartOptions.legend"
    ></apx-chart>
  `,
  standalone: true,
  imports: [NgApexchartsModule],
})
export class LineChartComponent implements OnInit {
  public chartOptions: ChartOptions;

  private releasedData: { x: number; y: number }[] = [];
  private purchasedData: { x: number; y: number }[] = [];

  private cumulativeReleased = 0;
  private cumulativePurchased = 0;
  public currentTime = new Date();

  constructor(
    private webSocketService: WebSocketService,
    private cdr: ChangeDetectorRef
  ) {
    this.chartOptions = {
      series: [
        {
          name: 'Tickets Released',
          data: this.releasedData,
          color: '#28a745',
        },
        {
          name: 'Tickets Purchased',
          data: this.purchasedData,
          color: '#dc3545',
        },
      ],
      chart: {
        type: 'line',
        height: 350,
        animations: {
          enabled: true,
          easing: 'linear',
          dynamicAnimation: {
            speed: 1000,
          },
        },
      },
      stroke: {
        curve: 'smooth',
      },
      dataLabels: {
        enabled: false,
      },
      xaxis: {
        type: 'datetime',
      },
      yaxis: {
        title: {
          text: 'Tickets',
        },
      },
      legend: {
        position: 'top',
      },
      title: {
        text: this.currentTime.toDateString(),
        align: 'left',
      },
    };
  }

  ngOnInit(): void {
    this.webSocketService.getTicketUpdates().subscribe((update: TicketUpdate) => {
      this.processTicketUpdate(update);
    });
  }

  private processTicketUpdate(update: TicketUpdate): void {
    const currentTime = new Date();

    if (update.action === 'ADD') {
      this.cumulativeReleased += update.tickets;
    } else if (update.action === 'REMOVE') {
      this.cumulativePurchased += update.tickets;
    }

    this.releasedData.push({ x: currentTime.getTime(), y: this.cumulativeReleased });
    this.purchasedData.push({ x: currentTime.getTime(), y: this.cumulativePurchased });


    if (this.releasedData.length > 20) {
      this.releasedData.shift();
      this.purchasedData.shift();
    }


    this.chartOptions.series = [
      {
        name: 'Tickets Released',
        data: [...this.releasedData],
      },
      {
        name: 'Tickets Purchased',
        data: [...this.purchasedData],
      },
    ];

    this.cdr.detectChanges();
  }
}
