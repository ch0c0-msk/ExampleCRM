package com.example.examplecrm.repos;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.id = ?1")
    Optional<Client> findById(Long id);

    @Query("select c from Client c where c.createUser = ?1")
    Iterable<Client> findByUser(User user);

    @Query("select c from Client c where c.rejectFlag = false order by c.fullName asc nulls last")
    List<Client> findByRejectNone();
}
