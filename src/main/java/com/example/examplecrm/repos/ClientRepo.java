package com.example.examplecrm.repos;

import com.example.examplecrm.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.id = ?1")
    Optional<Client> findById(Long id);
}
