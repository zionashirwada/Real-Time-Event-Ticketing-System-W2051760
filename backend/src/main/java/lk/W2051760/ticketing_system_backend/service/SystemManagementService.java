// src/main/java/lk/W2051760/ticketing_system_backend/service/SystemManagementService.java

package lk.W2051760.ticketing_system_backend.service;
import lk.W2051760.ticketing_system_backend.producer.VendorManager;
import lk.W2051760.ticketing_system_backend.model.Configuration;
import lk.W2051760.ticketing_system_backend.model.SystemState;
import lk.W2051760.ticketing_system_backend.consumer.CustomerManager;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SystemManagementService {

    private volatile SystemState currentState = SystemState.NOT_CONFIGURED;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private VendorManager vendorManager;

    @Autowired
    private CustomerManager customerManager;

    private Configuration configuration;
    private final int NUMBER_OF_VENDORS = 2;
    private final int NUMBER_OF_CUSTOMERS = 5;

    @PostConstruct
    public void initialize() {
        try {
            configuration = configurationService.loadConfiguration();
            if (configuration != null) {
                currentState = SystemState.STOPPED;
                // Initialize ticket pool and managers
                ticketPool.initialize(configuration.getMaxTicketCapacity());
                vendorManager.initialize(NUMBER_OF_VENDORS, configuration.getTicketReleaseRate());
                customerManager.initialize(NUMBER_OF_CUSTOMERS, configuration.getCustomerRetrievalRate());
                broadcastState();
            }
        } catch (IOException e) {
            // Configuration not found or error reading it
            currentState = SystemState.NOT_CONFIGURED;
            e.printStackTrace();
        }
    }

    public synchronized void startSystem() {
        if (currentState == SystemState.NOT_CONFIGURED) {
            throw new IllegalStateException("System is not configured. Please configure the system before starting.");
        }
        if (currentState == SystemState.RUNNING) {
            return; // Already running
        }
        currentState = SystemState.RUNNING;
        broadcastState();

        // Start vendors and customers
        vendorManager.startVendors();
        customerManager.startCustomers();
    }


    public synchronized void pauseSystem() {
        if (currentState != SystemState.RUNNING) {
            return; // Only running system can be paused
        }
        currentState = SystemState.PAUSED;
        broadcastState();

        // Pause vendors and customers
        vendorManager.pauseVendors();
        customerManager.pauseCustomers();
    }

    public synchronized void stopAndResetSystem() {
        if (currentState == SystemState.NOT_CONFIGURED) {
            return; // System is not configured
        }
        currentState = SystemState.STOPPED;
        broadcastState();

        // Stop vendors and customers
        vendorManager.stopVendors();
        customerManager.stopCustomers();

        // Reset ticket data
        ticketPool.reset();

        // Optionally, broadcast a reset update
        TicketUpdate update = new TicketUpdate(
                "RESET",
                "SYSTEM",
                "System Reset",
                0,
                ticketPool.getTotalTickets()
        );
        messagingTemplate.convertAndSend("/topic/ticket-updates", update);
    }

    public synchronized SystemState getCurrentState() {
        return currentState;
    }

    private void broadcastState() {
        messagingTemplate.convertAndSend("/topic/system-status", currentState.name());
    }
}
