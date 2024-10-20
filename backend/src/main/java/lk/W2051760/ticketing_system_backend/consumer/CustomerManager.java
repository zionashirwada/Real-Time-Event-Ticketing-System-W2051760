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

    private final List<Thread> customerThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final TicketPool ticketPool;
    private final TicketUpdateService ticketUpdateService;

    private int ticketsToPurchase; // Number of tickets each customer tries to purchase

    public CustomerManager(TicketPool ticketPool, TicketUpdateService ticketUpdateService) {
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
    }

    // Initialize with initial customer count and tickets to purchase
    public synchronized void initialize(int initialCustomerCount, int ticketsToPurchase) {
        this.ticketsToPurchase = ticketsToPurchase;
        for (int i = 0; i < initialCustomerCount; i++) {
            addCustomerThread();
        }
    }

    // Start all customers
    public synchronized void startCustomers() {
        for (Thread thread : customerThreads) {
            if (!thread.isAlive()) {
                thread.start();
            }
        }
        logger.info("All customer threads have been started.");
    }

    // Get the current number of customers
    public synchronized int getCustomerCount() {
        return customers.size();
    }
    // Pause all customers
    public synchronized void pauseCustomers() {
        for (Customer customer : customers) {
            customer.pause();
        }
        logger.info("All customer threads have been requested to pause.");
    }

    // Resume all customers
    public synchronized void resumeCustomers() {
        for (Customer customer : customers) {
            customer.resume();
        }
        logger.info("All customer threads have been requested to resume.");
    }

    // Add a customer
    public synchronized void addCustomer() {
        addCustomerThread();
        logger.info("Customer added. Total customers: {}", getCustomerCount());
    }

    // Remove a customer
    public synchronized void removeCustomer() {
        if (!customers.isEmpty()) {
            int lastIndex = customers.size() - 1;
            Customer customer = customers.remove(lastIndex);
            customer.stop();
            customerThreads.remove(lastIndex);
            logger.info("Customer removed. Total customers: {}", getCustomerCount());
        } else {
            logger.warn("No customers to remove.");
        }
    }

    // Stop all customers
    public synchronized void stopAllCustomers() {
        for (Customer customer : customers) {
            customer.stop();
        }
        customers.clear();
        customerThreads.clear();
        logger.info("All customer threads have been stopped.");
    }

    private void addCustomerThread() {
        String customerName = "Customer " + (customers.size() + 1);
        Customer customer = new Customer(customerName, ticketsToPurchase, ticketPool, ticketUpdateService);
        Thread thread = new Thread(customer, customerName + "-Thread");
        thread.setDaemon(true);
        customers.add(customer);
        customerThreads.add(thread);
        thread.start();
        logger.info("Started {}", thread.getName());
    }
}
