package com.example.examplecrm.repos;

import com.example.examplecrm.models.Deal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@DataJpaTest
public class DealRepoTest {

    @Autowired
    private DealRepo dealRepo;

    @Test
    public void shouldFindDealsByStatus() {

        List<Deal> result = dealRepo.findByStatus();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnCount() {

        Long result = dealRepo.findCount();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result);
    }

    @BeforeEach
    void setUp() {

        Deal firstDeal = new Deal();
        firstDeal.setId(1L);
        firstDeal.setStatus("NEW");
        dealRepo.save(firstDeal);

        Deal secondDeal = new Deal();
        secondDeal.setId(2L);
        secondDeal.setStatus("NEW");
        dealRepo.save(secondDeal);
    }
}
