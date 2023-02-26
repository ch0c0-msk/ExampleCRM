package com.example.examplecrm.repos;

import com.example.examplecrm.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.id = ?1")
    Optional<Product> findById(Long id);
}
