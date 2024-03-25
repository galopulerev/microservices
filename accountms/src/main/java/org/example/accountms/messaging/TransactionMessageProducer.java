package org.example.accountms.messaging;

import org.example.accountms.dto.TransactionMessage;
import org.example.accountms.model.Account;
import org.example.accountms.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Account account) {
        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setAccountNumber(account.getAccountNumber());
        transactionMessage.setClientId(account.getClientId());
        rabbitTemplate.convertAndSend("transactionQueue", transactionMessage);
    }
}
