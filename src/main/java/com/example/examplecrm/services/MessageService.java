package com.example.examplecrm.services;

import com.example.examplecrm.models.Message;
import com.example.examplecrm.models.User;
import com.example.examplecrm.repos.MessageRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final UserRepo userRepo;
    private final MessageRepo messageRepo;

    public boolean createMessage(Message message, Principal principal) {

        message.setStatus("NEW");
        message.setCreateUser(getUserByPrincipal(principal));
        messageRepo.save(message);
        log.info("Saving new message with attributes: {}",message.toString());
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) { return new User(); }
        return userRepo.findByLogin(principal.getName());
    }
}
