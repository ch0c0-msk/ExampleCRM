package com.example.examplecrm.repos;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DealRepo extends JpaRepository<Deal, Long> {

    @Query("select d from Deal d where d.id = ?1")
    Optional<Deal> findById(Long id);

    @Query("select d from Deal d where d.createUser = ?1")
    Iterable<Deal> findByUser(User user);

    @Query("select d from Deal d where d.status not in('SUCCESS','FAILED') order by d.createDate desc nulls last")
    List<Deal> findByStatus();

    @Query("select d from Deal d where d.status not in('SUCCESS','FAILED') and d.createUser = ?1 order by d.createDate desc nulls last")
    List<Deal> findByStatusAndUser(User user);

    @Query("select count(d) from Deal d")
    Long findCount();

}
