package com.example.examplecrm.controllers;

import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.UserRepo;
import com.example.examplecrm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

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
        return "redirect:/users_list";
    }

    @GetMapping("users_list")
    public String userList(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users",users);
        return "users_list";
    }

    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {

        Optional<User> user = userRepo.findById(id);
        ArrayList<User> userDetails = new ArrayList<>();
        user.ifPresent(userDetails::add);
        model.addAttribute("users",userDetails);
        model.addAttribute("roles", Role.values());
        return "edit_user";
    }

    @PostMapping("/edit_user/{id}")
    public String modifyUser(@RequestParam("userId") User user, @RequestParam Map<String,String> form, @RequestParam String login) {

        user.setLogin(login);
        userService.modifyUser(user);
        userService.modifyUserRoles(user, form);
        return "redirect:/users_list";
    }

    @PostMapping("/disable_user/{id}")
    public String disableUser(@PathVariable(value = "id") Long id) {

        userService.disableUser(id);
        return "redirect:/users_list";
    }

    @PostMapping("/enable_user/{id}")
    public String enableUser(@PathVariable(value = "id") Long id) {

        userService.enableUser(id);
        return "redirect:/users_list";
    }
}
