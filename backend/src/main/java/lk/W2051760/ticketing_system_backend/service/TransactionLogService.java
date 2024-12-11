/**
 * TransactionLogService.java
 * 
 * This file contains the TransactionLogService class, which is responsible for 
 * broadcasting transaction logs to clients via WebSocket messaging.
 */
package lk.W2051760.ticketing_system_backend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lk.W2051760.ticketing_system_backend.model.TransactionLog;

@Service
public class TransactionLogService {

    private static final Logger logger = LogManager.getLogger(TransactionLogService.class);

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructor for TransactionLogService.
     * 
     * @param messagingTemplate the SimpMessagingTemplate used for sending messages.
     */
    public TransactionLogService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a transaction log to the specified WebSocket topic.
     * 
     * @param log the TransactionLog object containing the transaction details to
     *            broadcast.
     */
    public void sendTransactionLog(TransactionLog log) {
        messagingTemplate.convertAndSend("/topic/transaction-logs", log);
        logger.info("Broadcasted transaction log: {}", log);
    }
}
