package com.example.examplecrm.repos;

import com.example.examplecrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
