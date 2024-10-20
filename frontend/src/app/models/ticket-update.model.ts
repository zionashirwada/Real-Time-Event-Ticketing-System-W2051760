export interface TicketUpdate {
  action: string;        // "ADD", "REMOVE"
  entity: string;        // "VENDOR", "CUSTOMER", "SYSTEM"
  name: string;          // Name of the vendor/customer
  tickets: number;       // Number of tickets added/removed
  totalTickets: number;  // Current total tickets in the pool
}
