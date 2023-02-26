package com.example.examplecrm.services;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
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

    public boolean modifyUser(User user) {

        if (user == null) {
            log.info("User with id: {} doesnt exist", user.getId());
            return false;
        } else {
            userRepo.save(user);
            log.info("Modify client with new attributes: {}",user.toString());
            return true;
        }
    }

    public boolean disableUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.info("Client with id: {} doesnt exist", id);
            return false;
        } else {
            user.setActive(false);
            userRepo.save(user);
            log.info("Client with attributes: {} was disabled", user.toString());
            return true;
        }
    }

    public boolean enableUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.info("Client with id: {} doesnt exist", id);
            return false;
        } else {
            user.setActive(true);
            userRepo.save(user);
            log.info("Client with attributes: {} was enabled", user.toString());
            return true;
        }
    }

    public void modifyUserRoles(User user, Map<String,String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }
}
