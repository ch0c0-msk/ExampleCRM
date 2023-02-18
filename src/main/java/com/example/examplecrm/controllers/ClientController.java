package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepo clientRepo;
    private final ClientService clientService;

    @GetMapping("/clients_list")
    public String clientList(Model model) {
        Iterable<Client> clients = clientRepo.findAll();
        model.addAttribute("clients",clients);
        return "clients_list";
    }

    @GetMapping("/add_client")
    public String addClient(Model model) {

        return "add_client";
    }

    @PostMapping("add_client")
    public String createClient(@RequestParam String fullName, @RequestParam String email) {

        Client client = new Client();
        client.setFullName(fullName);
        client.setEmail(email);
        clientService.createClient(client);
        return "redirect:/clients_list";
    }
}


