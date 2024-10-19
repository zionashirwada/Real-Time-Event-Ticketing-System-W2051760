package lk.W2051760.ticketing_system_backend.consumer;

import jakarta.annotation.PreDestroy;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerManager {
    private static final Logger logger = LogManager.getLogger(CustomerManager.class);

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
            logger.info("Initialized {}", thread.getName());
        }
    }

    public void startCustomers() {
        for (Thread thread : customerThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

    public void stopCustomers() {
        for (Customer customer : customers) {
            customer.stop();
        }
        logger.info("All customer threads have been requested to stop.");
    }

    @PreDestroy
    public void onDestroy() {
        stopCustomers();
        logger.info("CustomerManager is being destroyed. Customer threads stopped.");
    }
}
