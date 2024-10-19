package lk.W2051760.ticketing_system_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import lk.W2051760.ticketing_system_backend.service.ConfigurationService;
import lk.W2051760.ticketing_system_backend.model.Configuration;
import lk.W2051760.ticketing_system_backend.service.TicketPool;
import lk.W2051760.ticketing_system_backend.producer.VendorManager;

@SpringBootApplication
public class TicketingSystemBackendApplication {

	public static void main(String[] args) {
		// Start and get the context
		ConfigurableApplicationContext context = SpringApplication.run(TicketingSystemBackendApplication.class, args);

		// Get ConfigurationService bean from the context
		ConfigurationService configurationService = context.getBean(ConfigurationService.class);
		TicketPool ticketPool = context.getBean(TicketPool.class);
		VendorManager vendorManager = context.getBean(VendorManager.class);


		// Load  config
		Configuration config = null;
		try {
			config = configurationService.loadConfiguration();
		} catch (Exception e) {
			e.printStackTrace();

			// set defalt
			config = new Configuration(0, 0, 0, 0);
		}

		// instance  of TicketPool from maxTicketCapacity
		int maxTicketCapacity = config.getMaxTicketCapacity();
		ticketPool.initialize(maxTicketCapacity);

		//  instance of VendorManager from ticketReleaseRate.
		int ticketReleaseRate = config.getTicketReleaseRate();
		int numberOfVendors = 3;
		vendorManager.initialize(numberOfVendors, ticketReleaseRate);

		// Start vendors
		vendorManager.startVendors();


		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down vendors...");
			vendorManager.stopVendors();
		}));
	}

}
