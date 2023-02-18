package com.example.examplecrm.services;

import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

    public boolean createUser(User user) {
        String login = user.getLogin();
        if (userRepo.findByLogin(login) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.USER);
        userRepo.save(user);
        log.info("Saving new user with attributes: ",user.toString());
        return true;
    }
}
