package com.example.examplecrm.controllers;

import com.example.examplecrm.models.User;
import com.example.examplecrm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

}
