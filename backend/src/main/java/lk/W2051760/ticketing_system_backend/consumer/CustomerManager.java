package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerManager {

    private List<Thread> customerThreads;
    private List<Customer> customers;
    private TicketPool ticketPool;

    public CustomerManager(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerThreads = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void initialize(int numberOfCustomers, int ticketsToPurchase) {
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer("Customer " + i, ticketsToPurchase, ticketPool);
            Thread thread = new Thread(customer, "CustomerThread-" + i);
            // Set as daemon to allow JVM to exit if only daemon threads are running :Dont know why i did this :(
            thread.setDaemon(true);
            customers.add(customer);
            customerThreads.add(thread);
        }
    }

    public void startCustomers() {
        for (Thread thread : customerThreads) {
            thread.start();
        }
    }

    public void stopCustomers() {
        for (Customer customer : customers) {
            customer.stop();
        }
        System.out.println("All customer threads have been requested to stop.");
    }
}
