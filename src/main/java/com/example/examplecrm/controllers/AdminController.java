package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.User;
import com.example.examplecrm.repos.UserRepo;
import com.example.examplecrm.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/add_user")
    public String addUser() {
        return "add_user";
    }


    @PostMapping("/add_user")
    public String createUser(@RequestParam String login, @RequestParam String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("users_list")
    public String userList(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users",users);
        return "users_list";
    }
}
