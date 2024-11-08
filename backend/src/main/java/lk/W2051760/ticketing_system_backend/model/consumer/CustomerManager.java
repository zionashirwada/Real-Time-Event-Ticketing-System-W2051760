package lk.W2051760.ticketing_system_backend.model.consumer;

import lk.W2051760.ticketing_system_backend.service.CountUpdateService;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.service.TicketUpdateService;
import lk.W2051760.ticketing_system_backend.service.TransactionLogService;
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
    private final CountUpdateService countUpdateService;
    private final TransactionLogService transactionLogService;

    public CustomerManager(TicketPool ticketPool, TicketUpdateService ticketUpdateService,
                           CountUpdateService countUpdateService,TransactionLogService transactionLogService) {
        this.ticketPool = ticketPool;
        this.ticketUpdateService = ticketUpdateService;
        this.countUpdateService = countUpdateService;
        this.customerThreads = new ArrayList<>();
        this.transactionLogService = transactionLogService;
        this.customers = new ArrayList<>();
    }

    // Initialize with the necessary parameters
    public void initialize(int numberOfCustomers, int ticketsToPurchase) {
        this.numberOfCustomers = numberOfCustomers;
        this.ticketsToPurchase = ticketsToPurchase;
        countUpdateService.updateCustomerCount(getCustomerCount());
    }

    public void startCustomers() {
        // Clear previous customers and threads
        customers.clear();
        customerThreads.clear();

        // Create new Customer instances and threads
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer("Customer " + i, ticketsToPurchase, ticketPool, ticketUpdateService,transactionLogService);
            Thread thread = new Thread(customer, "CustomerThread-" + i);
            thread.setDaemon(true);
            customers.add(customer);
            customerThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
            countUpdateService.updateCustomerCount(getCustomerCount());
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
    // Add a customer
    public synchronized void addCustomer() {
        addCustomerThread();
        logger.info("Customer added. Total customers: {}", getCustomerCount());
        countUpdateService.updateCustomerCount(getCustomerCount());
    }

    // Remove a customer
    public synchronized void removeCustomer() {
        if (!customers.isEmpty()) {
            int lastIndex = customers.size() - 1;
            Customer customer = customers.remove(lastIndex);
            customer.stop();
            customerThreads.remove(lastIndex);
            logger.info("Customer removed. Total customers: {}", getCustomerCount());
            countUpdateService.updateCustomerCount(getCustomerCount());
        } else {
            logger.warn("No customers to remove.");
        }
    }
    // Get the current number of customers
    public synchronized int getCustomerCount() {
        return customers.size();
    }
    public void stopCustomers() {
        for (Customer customer : customers) {
            customer.stop();
        }
        logger.info("All customer threads have been requested to stop.");
    }

    private void addCustomerThread() {
        String customerName = "Customer " + (customers.size() + 1);
        Customer customer = new Customer(customerName, ticketsToPurchase, ticketPool, ticketUpdateService,transactionLogService);
        Thread thread = new Thread(customer, customerName + "-Thread");
        thread.setDaemon(true);
        customers.add(customer);
        customerThreads.add(thread);
        thread.start();
        logger.info("Started {}", thread.getName());
    }
}
