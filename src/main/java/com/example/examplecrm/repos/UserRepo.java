package com.example.examplecrm.repos;

import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query("select u from User u where u.login = ?1")
    User findByLogin(String login);
}
