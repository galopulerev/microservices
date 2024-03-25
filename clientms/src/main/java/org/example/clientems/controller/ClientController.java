package org.example.clientems.controller;

import org.example.clientems.model.Client;
import org.example.clientems.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService personaService) {
        this.clientService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient != null) {
            existingClient.setName(client.getName());
            existingClient.setGenre(client.getGenre());
            existingClient.setAge(client.getAge());
            existingClient.setIdentification(client.getIdentification());
            existingClient.setAddress(client.getAddress());
            existingClient.setPhone(client.getPhone());
            existingClient.setPassword(client.getPassword());
            existingClient.setStatus(client.getStatus());

            Client updatedClient = clientService.saveClient(existingClient);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el id: " + id);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        Client client = this.clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        boolean isDeleted = this.clientService.deleteClientById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Cliente eliminado satisfactoriamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
    }

}
