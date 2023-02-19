package com.example.examplecrm.services;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepo clientRepo;

    public boolean createClient(Client client) {

        clientRepo.save(client);
        log.info("Saving new client with attributes: {}",client.toString());
        return true;
    }

    public boolean modifyClient(Client client) {

        if (clientRepo.findById(client.getId()) == null) {
            log.info("Client with id: {} doesnt exist", client.getId());
            return false;
        } else {
            clientRepo.save(client);
            log.info("Modify client with new attributes: {}",client.toString());
            return true;
        }
    }

    public boolean deleteClient(Long id) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            log.info("Client with id: {} doesnt exist", id);
            return false;
        } else {
            clientRepo.delete(client);
            log.info("Client with attributes: {} was deleted", client.toString());
            return true;
        }
    }
}
