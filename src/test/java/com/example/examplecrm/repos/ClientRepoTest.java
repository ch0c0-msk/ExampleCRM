package com.example.examplecrm.repos;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@DataJpaTest
public class ClientRepoTest {

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    UserRepo userRepo;

    @Test
    public void shouldFindClientsByRejectFlag() {

        Client client = new Client();
        client.setRejectFlag(false);
        clientRepo.save(client);

        List<Client> clientList = clientRepo.findByRejectNone();

        Assertions.assertNotNull(clientList);
        Assertions.assertEquals(1, clientList.size());
    }

    @Test
    public void shouldFindClientsByUser() {

        Client client = new Client();
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepo.save(user);
        client.setCreateUser(user);
        clientRepo.save(client);

        Iterable<Client> clients = clientRepo.findByUser(user);
        List<Client> clientList = new ArrayList<Client>();
        clients.forEach(clientList::add);

        Assertions.assertNotNull(clientList);
        Assertions.assertEquals(1, clientList.size());
    }

    @Test
    public void shouldFindClientsByRejectFlagAndUser() {

        Client client = new Client();
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepo.save(user);
        client.setCreateUser(user);
        client.setRejectFlag(false);
        clientRepo.save(client);

        List<Client> clientList = clientRepo.findByRejectNoneAndUser(user);

        Assertions.assertNotNull(clientList);
        Assertions.assertEquals(1, clientList.size());
    }
}
