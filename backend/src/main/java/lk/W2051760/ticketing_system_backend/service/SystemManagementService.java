/**
 * SystemManagementService.java
 * 
 * This file contains the SystemManagementService class, which manages the overall
 * state and lifecycle of the real-time ticketing system. It handles system initialization,
 * state transitions (start, pause, stop, and reset), and broadcasts updates to clients
 * via WebSocket messaging.
 */

package lk.W2051760.ticketing_system_backend.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lk.W2051760.ticketing_system_backend.model.Configuration;
import lk.W2051760.ticketing_system_backend.model.SystemState;
import lk.W2051760.ticketing_system_backend.model.TicketUpdate;
import lk.W2051760.ticketing_system_backend.model.consumer.CustomerManager;
import lk.W2051760.ticketing_system_backend.model.producer.VendorManager;

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

    @Autowired
    private CountUpdateService countUpdateService;

    private Configuration configuration;

    private final int NUMBER_OF_VENDORS = 1;
    private final int NUMBER_OF_CUSTOMERS = 1;

    private static final Logger logger = LoggerFactory.getLogger(SystemManagementService.class);

    /**
     * Initializes the system configuration and sets up the initial state.
     * 
     * This method is called automatically after the bean's properties have been
     * set.
     * It loads the configuration, initializes system components, and broadcasts the
     * initial system state.
     */
    @PostConstruct
    public void initialize() {
        try {
            configuration = configurationService.loadConfiguration();
            if (configuration != null) {
                currentState = SystemState.STOPPED;
                // Initialize ticket pool and managers
                ticketPool.initialize(configuration.getMaxTicketCapacity(), configuration.getTotalSystemTickets());
                vendorManager.initialize(NUMBER_OF_VENDORS, configuration.getTicketReleaseRate());
                customerManager.initialize(NUMBER_OF_CUSTOMERS, configuration.getCustomerRetrievalRate());
                broadcastState();
                // Send initial counts
                countUpdateService.updateVendorCount(vendorManager.getVendorCount());
                countUpdateService.updateCustomerCount(customerManager.getCustomerCount());
            }
        } catch (IOException e) {
            // Configuration not found or error reading it
            currentState = SystemState.NOT_CONFIGURED;
            e.printStackTrace();
        }
    }

    /**
     * Starts the system if it is not already running and is properly configured.
     * 
     * @throws IllegalStateException if the system is not configured.
     */
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
        countUpdateService.updateVendorCount(vendorManager.getVendorCount());
        countUpdateService.updateCustomerCount(customerManager.getCustomerCount());
    }

    /**
     * Pauses the system if it is currently running.
     */
    public synchronized void pauseSystem() {
        if (currentState != SystemState.RUNNING) {
            return; // Only running system can be paused
        }
        currentState = SystemState.PAUSED;
        broadcastState();

        // Pause vendors and customers
        vendorManager.pauseVendors();
        customerManager.pauseCustomers();
        countUpdateService.updateVendorCount(vendorManager.getVendorCount());
        countUpdateService.updateCustomerCount(customerManager.getCustomerCount());
    }

    /**
     * Stops the system and resets its state. This method is synchronized to ensure
     * thread safety during the stop and reset operations. If the system is not
     * configured, the method returns immediately without performing any actions.
     * 
     * The method performs the following actions:
     * - Sets the current state to STOPPED and broadcasts the state change.
     * - Stops all vendors and customers.
     * - Resets the ticket data pool.
     * - Resets vendor and customer counts.
     * - Broadcasts a system reset update message.
     */
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

        // Reset counts
        countUpdateService.updateVendorCount(1);
        countUpdateService.updateCustomerCount(1);
        countUpdateService.updateVendorCount(vendorManager.getVendorCount());
        countUpdateService.updateCustomerCount(customerManager.getCustomerCount());

        // broadcast a reset update
        TicketUpdate update = new TicketUpdate(
                "RESET",
                "SYSTEM",
                "System Reset",
                0,
                ticketPool.getPoolTicketAmount());
        messagingTemplate.convertAndSend("/topic/ticket-updates", update);
    }

    /**
     * Retrieves the current system state.
     * 
     * @return the current SystemState of the system.
     */
    public synchronized SystemState getCurrentState() {
        return currentState;
    }

    private void broadcastState() {
        messagingTemplate.convertAndSend("/topic/system-status", currentState.name());
    }

    /**
     * Reinitializes the system with a new configuration.
     * 
     * @param config the new Configuration object to apply.
     */
    public void reinitializeSystem(Configuration config) {
        // Stop any running processes first
        if (currentState != SystemState.STOPPED && currentState != SystemState.NOT_CONFIGURED) {
            stopAndResetSystem();
        }

        // Initialize system with new configuration
        ticketPool.initialize(config.getMaxTicketCapacity(), config.getTotalSystemTickets());
        vendorManager.initialize(1, config.getTicketReleaseRate());
        customerManager.initialize(1, config.getCustomerRetrievalRate());

        // Update system state
        currentState = SystemState.STOPPED;

        // Notify clients about the state change and new configuration
        messagingTemplate.convertAndSend("/topic/system-status", currentState.toString());
        messagingTemplate.convertAndSend("/topic/configuration-update", config);

        logger.info("System reinitialized with new configuration");
    }
}
