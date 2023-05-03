package com.example.examplecrm.services;


import com.example.examplecrm.models.Client;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private ClientRepo clientRepo;
    private ClientService clientService;
    private UserRepo userRepo;

    @Test
    public void shouldReturnClientsByRejectNone() {

        List<Client> clientList = createClients();
        Mockito.when(clientRepo.findByRejectNone()).thenReturn(clientList);

        List<Client> result = clientService.getListAllForExport();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    private List<Client> createClients() {

        Client firstClient = new Client();
        Client secondClient = new Client();

        firstClient.setId(1L);
        firstClient.setRejectFlag(false);
        secondClient.setId(2L);
        secondClient.setRejectFlag(true);

        return List.of(firstClient, secondClient);
    }

    @BeforeEach
    void setUp() {

        clientRepo = Mockito.mock(ClientRepo.class);
        userRepo = Mockito.mock(UserRepo.class);
        clientService = new ClientService(clientRepo, userRepo);
    }
}
