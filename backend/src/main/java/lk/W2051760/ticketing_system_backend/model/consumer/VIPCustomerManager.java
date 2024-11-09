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
public class VIPCustomerManager extends CustomerManager {
    private static final Logger logger = LogManager.getLogger(VIPCustomerManager.class);
    
    private List<Thread> vipCustomerThreads;
    private List<VIPCustomer> vipCustomers;
    private int numberOfVIPCustomers;

    public VIPCustomerManager(TicketPool ticketPool, 
                            TicketUpdateService ticketUpdateService,
                            CountUpdateService countUpdateService, 
                            TransactionLogService transactionLogService) {
        super(ticketPool, ticketUpdateService, countUpdateService, transactionLogService);
        this.vipCustomerThreads = new ArrayList<>();
        this.vipCustomers = new ArrayList<>();
    }

    @Override
    public void initialize(int numberOfCustomers, int ticketsToPurchase) {
        this.numberOfVIPCustomers = numberOfCustomers;
        super.initialize(numberOfCustomers, ticketsToPurchase);
        vipCustomers = new ArrayList<>();
        vipCustomerThreads = new ArrayList<>();
    }

    @Override
    public void startCustomers() {
        vipCustomers.clear();
        vipCustomerThreads.clear();

        for (int i = 1; i <= numberOfVIPCustomers; i++) {
            VIPCustomer vipCustomer = new VIPCustomer(
                i,
                "VIP Customer " + i, 
                getTicketsToPurchase(),
                getTicketPool(),
                getTicketUpdateService(),
                getTransactionLogService()
            );
            
            Thread thread = new Thread(vipCustomer, "VIPCustomerThread-" + i);
            thread.setDaemon(true);
            vipCustomers.add(vipCustomer);
            vipCustomerThreads.add(thread);
            logger.info("Initialized {}", thread.getName());
            getCountUpdateService().updateVIPCustomerCount(getVIPCustomerCount());
        }

        for (Thread thread : vipCustomerThreads) {
            thread.start();
            logger.info("Started {}", thread.getName());
        }
    }

    public void pauseVIPCustomers() {
        for (VIPCustomer customer : vipCustomers) {
            customer.pause();
        }
        logger.info("All VIP customer threads have been requested to pause.");
    }

    public void resumeVIPCustomers() {
        for (VIPCustomer customer : vipCustomers) {
            customer.resume();
        }
        logger.info("All VIP customer threads have been requested to resume.");
    }

    public synchronized int getVIPCustomerCount() {
        return vipCustomers.size();
    }

    public synchronized void addVIPCustomer() {
        addVIPCustomerThread();
        logger.info("VIP Customer added. Total VIP customers: {}", getVIPCustomerCount());
        getCountUpdateService().updateVIPCustomerCount(getVIPCustomerCount());
    }

    public synchronized void removeVIPCustomer() {
        if (!vipCustomers.isEmpty()) {
            int lastIndex = vipCustomers.size() - 1;
            VIPCustomer customer = vipCustomers.remove(lastIndex);
            customer.stop();
            vipCustomerThreads.remove(lastIndex);
            logger.info("VIP Customer removed. Total VIP customers: {}", getVIPCustomerCount());
            getCountUpdateService().updateVIPCustomerCount(getVIPCustomerCount());
        } else {
            logger.warn("No VIP customers to remove.");
        }
    }

    public void stopVIPCustomers() {
        for (VIPCustomer customer : vipCustomers) {
            customer.stop();
        }
        logger.info("All VIP customer threads have been requested to stop.");
    }

    private void addVIPCustomerThread() {
        String customerName = "VIP Customer " + (vipCustomers.size() + 1);
        int customerId = vipCustomers.size() + 1;
        VIPCustomer customer = new VIPCustomer(
            customerId,
            customerName, 
            getTicketsToPurchase(),
            getTicketPool(),
            getTicketUpdateService(),
            getTransactionLogService()
        );
        
        Thread thread = new Thread(customer, customerName + "-Thread");
        thread.setDaemon(true);
        vipCustomers.add(customer);
        vipCustomerThreads.add(thread);
        thread.start();
        logger.info("Started {}", thread.getName());
    }
} 