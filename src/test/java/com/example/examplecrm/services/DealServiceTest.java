package com.example.examplecrm.services;

import com.example.examplecrm.models.Deal;
import com.example.examplecrm.repos.DealRepo;
import com.example.examplecrm.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    private DealService dealService;
    private DealRepo dealRepo;
    private UserRepo userRepo;
    private EmailService emailService;

    @Test
    public void shouldReturnDealsByStatus() {

        List<Deal> dealList = createDeals();
        Mockito.when(dealRepo.findByStatus()).thenReturn(createDeals());

        List<Deal> result = dealService.getListAllForExport();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @BeforeEach
    void setUp() {

        dealRepo = Mockito.mock(DealRepo.class);
        userRepo = Mockito.mock(UserRepo.class);
        emailService = Mockito.mock(EmailService.class);
        dealService = new DealService(dealRepo, userRepo, emailService);
    }

    private List<Deal> createDeals() {

        Deal firstDeal = new Deal();
        firstDeal.setId(1L);
        firstDeal.setStatus("NEW");

        Deal secondDeal = new Deal();
        secondDeal.setId(2L);
        secondDeal.setStatus("NEW");

        return List.of(firstDeal, secondDeal);
    }
}
