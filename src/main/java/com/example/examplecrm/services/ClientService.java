package com.example.examplecrm.services;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepo clientRepo;
    private final UserRepo userRepo;

    public boolean createClient(Principal principal, Client client) {
        client.setCreateUser((getUserByPrincipal(principal)));
        clientRepo.save(client);
        log.info("Saving new client with attributes: {}",client.toString());
        return true;
    }

    public boolean modifyClient(Client client, Principal principal) {

        if (client.getCreateUser() == getUserByPrincipal(principal) | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {

            if (clientRepo.findById(client.getId()) == null) {
                log.info("Client with id: {} doesnt exist", client.getId());
                return false;
            } else {
                client.setUpdateUser(getUserByPrincipal(principal));
                clientRepo.save(client);
                log.info("Modify client with new attributes: {}", client.toString());
                return true;
            }
        } else {
            log.info("It`s not your client or you haven`t a Manager role");
            return false;
        }
    }

    public boolean deleteClient(Long id, Principal principal) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            log.info("Client with id: {} doesnt exist", id);
            return false;
        } else {
            if (client.getCreateUser() == getUserByPrincipal(principal) | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {

                clientRepo.delete(client);
                log.info("Client with attributes: {} was deleted", client.toString());
                return true;
            } else {

                log.info("It`s not your client or you haven`t a Manager role");
                return false;
            }
        }
    }

    public boolean changeRejectClient(Long id, Principal principal) {

        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            log.info("Client with id: {} doesnt exist", id);
            return false;
        } else {
            if (client.getCreateUser() == getUserByPrincipal(principal) | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {

                client.setRejectFlag(!client.getRejectFlag());
                clientRepo.save(client);
                log.info("Reject flag successfully changed");
                return true;
            } else {

                log.info("It`s not your client or you haven`t a Manager role");
                return false;
            }
        }
    }

    public List<Client> getListAllForExport() {
        return clientRepo.findByRejectNone();
    }

    public List<Client> getListForExport(Principal principal) {
        return clientRepo.findByRejectNoneAndUser(getUserByPrincipal(principal));
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) { return new User(); }
        return userRepo.findByLogin(principal.getName());
    }
}
