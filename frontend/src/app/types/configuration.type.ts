export class Configuration {
  totalTickets: number;
  ticketReleaseRate: number;
  customerRetrievalRate: number;
  maxTicketCapacity: number;

  constructor() {
    this.totalTickets = 0;
    this.ticketReleaseRate = 0;
    this.customerRetrievalRate = 0;
    this.maxTicketCapacity = 0;
  }
}
