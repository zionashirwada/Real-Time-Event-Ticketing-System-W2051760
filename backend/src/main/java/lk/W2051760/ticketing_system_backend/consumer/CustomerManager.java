package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerManager {

    private static final Logger logger = LogManager.getLogger(CustomerManager.class);

    private List<Thread> customerThreads;
    private List<Customer> customers;
    private TicketPool ticketPool;
    private final TicketUpdateService ticketUpdateService;

    private int numberOfCustomers;
    private int ticketsToPurchase;

    public CustomerManager(TicketPool ticketPool, TicketUpdateService ticketUpdateService) {
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
        this.customerThreads = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    // Initialize with the necessary parameters
    public void initialize(int numberOfCustomers, int ticketsToPurchase) {
        this.numberOfCustomers = numberOfCustomers;
        this.ticketsToPurchase = ticketsToPurchase;
    }

    public void startCustomers() {
        // Clear previous customers and threads
        customers.clear();
        customerThreads.clear();

        // Create new Customer instances and threads
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer("Customer " + i, ticketsToPurchase, ticketPool, ticketUpdateService);
            Thread thread = new Thread(customer, "CustomerThread-" + i);
            thread.setDaemon(true);
            customers.add(customer);
            customerThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
        }

        // Start new threads
        for (Thread thread : customerThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

    public void pauseCustomers() {
        for (Customer customer : customers) {
            customer.pause();
        }
        logger.info("All customer threads have been requested to pause.");
    }

    public void resumeCustomers() {
        for (Customer customer : customers) {
            customer.resume();
        }
        logger.info("All customer threads have been requested to resume.");
    }

    public void stopCustomers() {
        for (Customer customer : customers) {
            customer.stop();
        }
        logger.info("All customer threads have been requested to stop.");
    }
}
