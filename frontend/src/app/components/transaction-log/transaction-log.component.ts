import { Component, OnInit } from '@angular/core';
import { TransactionLog } from '../../models/transaction-log.model';
import { WebSocketService } from '../../services/web-socket-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-log',
  templateUrl: './transaction-log.component.html',
  styleUrls: ['./transaction-log.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class TransactionLogComponent implements OnInit {
  transactionLogs: TransactionLog[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.getTransactionLogs().subscribe((log: TransactionLog) => {
      this.transactionLogs.unshift(log);
      // Limit to last 50 logs
      if (this.transactionLogs.length > 50) {
        this.transactionLogs.pop();
      }
    });
  }
}
