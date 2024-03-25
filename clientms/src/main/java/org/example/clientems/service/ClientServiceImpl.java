package org.example.clientems.service;

import org.example.clientems.clients.AccountClient;
import org.example.clientems.model.Client;
import org.example.clientems.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AccountClient accountClient;

    public ClientServiceImpl(ClientRepository clientRepository, AccountClient accountClient) {
        this.clientRepository = clientRepository;
        this.accountClient = accountClient;
    }

    @Override
    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client saveClient(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public boolean deleteClientById(Long id) {
        if (this.clientRepository.existsById(id)) {
            this.clientRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Client getClientById(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    @Override
    public void updateAccount(String accountNumber, Long clientId) {
        Client client = this.getClientById(clientId);
        System.out.println("The name for clientId: " + clientId + " is " + client.getName());
        accountClient.updateClientName(accountNumber, client.getName());
    }


}
