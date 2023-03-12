package com.example.examplecrm.repos;

import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DealRepo extends JpaRepository<Deal, Long> {

    @Query("select d from Deal d where d.id = ?1")
    Optional<Deal> findById(Long id);

    @Query("select d from Deal d where d.createUser = ?1")
    Iterable<Deal> findByUser(User user);
}
