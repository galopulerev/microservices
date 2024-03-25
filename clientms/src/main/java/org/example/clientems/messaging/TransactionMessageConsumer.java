package org.example.clientems.messaging;

import org.example.clientems.dto.TransactionMessage;
import org.example.clientems.model.Client;
import org.example.clientems.service.ClientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageConsumer {
    private final ClientService clientService;

    public TransactionMessageConsumer(ClientService clientService) {
        this.clientService = clientService;
    }

    @RabbitListener(queues = "transactionQueue")
    public void consumeMessage(TransactionMessage transactionMessage){
        Client client = new Client();
        client.setPersonId(transactionMessage.getClientId());
        this.clientService.updateAccount(transactionMessage.getAccountNumber(), client.getPersonId());
    }
}