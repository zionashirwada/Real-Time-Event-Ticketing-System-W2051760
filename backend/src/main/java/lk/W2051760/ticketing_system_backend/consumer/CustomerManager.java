package lk.W2051760.ticketing_system_backend.consumer;

import lk.W2051760.ticketing_system_backend.controller.TicketUpdateController;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerManager {

    private static final Logger logger = LogManager.getLogger(CustomerManager.class);

    private List<Thread> customerThreads;
    private List<Customer> customers;
    private TicketPool ticketPool;
    private final TicketUpdateController ticketUpdateController;

    public CustomerManager(TicketPool ticketPool, TicketUpdateController ticketUpdateController) {
        this.ticketPool = ticketPool;
        this.ticketUpdateController = ticketUpdateController;
        this.customerThreads = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

//    Initialize
    public void initialize(int numberOfCustomers, int ticketsToPurchase) {
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer("Customer " + i, ticketsToPurchase, ticketPool, ticketUpdateController);
            Thread thread = new Thread(customer, "CustomerThread-" + i);
            thread.setDaemon(true); // Set as daemon to allow JVM to exit if only daemon threads are running
            customers.add(customer);
            customerThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
        }
    }

//    Start cus
    public void startCustomers() {
        for (Thread thread : customerThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

//    End Cus
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
