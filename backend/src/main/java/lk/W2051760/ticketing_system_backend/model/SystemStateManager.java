// src/main/java/lk/W2051760/ticketing_system_backend/model/SystemStateManager.java

package lk.W2051760.ticketing_system_backend.model;

import lk.W2051760.ticketing_system_backend.consumer.CustomerManager;
import lk.W2051760.ticketing_system_backend.producer.VendorManager;
import lk.W2051760.ticketing_system_backend.service.TicketPool;

public class SystemStateManager {
    private volatile SystemState currentState = SystemState.STOPPED;
    public synchronized void setCurrentState(SystemState state) {
        currentState = state;
        notifyAll();
    }

    public synchronized SystemState getCurrentState() {
        return currentState;
    }

    public synchronized void waitForRunning() throws InterruptedException {
        while (currentState != SystemState.RUNNING) {
            wait();
        }
    }
}
