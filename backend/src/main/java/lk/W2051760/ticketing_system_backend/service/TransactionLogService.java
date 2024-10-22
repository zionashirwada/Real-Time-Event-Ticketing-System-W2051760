package lk.W2051760.ticketing_system_backend.service;

import lk.W2051760.ticketing_system_backend.model.TransactionLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionLogService {

    private static final Logger logger = LogManager.getLogger(TransactionLogService.class);

    private final SimpMessagingTemplate messagingTemplate;

    public TransactionLogService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendTransactionLog(TransactionLog log) {
        messagingTemplate.convertAndSend("/topic/transaction-logs", log);
        logger.info("Broadcasted transaction log: {}", log);
    }
}
