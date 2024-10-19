package lk.W2051760.ticketing_system_backend.producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lk.W2051760.ticketing_system_backend.service.TicketPool;

public class Vendor implements Runnable {
    private static final Logger logger = LogManager.getLogger(Vendor.class);

    private String vendorName;
    private int ticketReleaseRate;
    private TicketPool ticketPool;
    private volatile boolean running;

    public Vendor(String vendorName, int ticketReleaseRate, TicketPool ticketPool) {
        this.vendorName = vendorName;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Add tickets to the ticket pool
                ticketPool.addTickets(ticketReleaseRate);
                logger.info("{} released {} tickets.", vendorName, ticketReleaseRate);

                // pause time
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("{} was interrupted.", vendorName, e);
            }
        }
        logger.info("{} has stopped.", vendorName);
    }

    public void stop() {

        running = false;
    }
}
