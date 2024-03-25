package org.example.clientems.service;

import org.example.clientems.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client saveClient(Client client);

    boolean deleteClientById(Long id);

    Client getClientById(Long id);

    void updateAccount(String accountNumber, Long clientId);

}
