package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

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
    public String createClient(Principal principal, @RequestParam String fullName, @RequestParam String email) {

        Client client = new Client();
        client.setFullName(fullName);
        client.setEmail(email);
        clientService.createClient(principal, client);
        return "redirect:/clients_list";
    }

    @GetMapping("/edit_client/{id}")
    public String editClient(@PathVariable(value = "id") Long id, Model model) {

        Optional<Client> client = clientRepo.findById(id);
        ArrayList<Client> clientDetails = new ArrayList<>();
        client.ifPresent(clientDetails::add);
        model.addAttribute("clients",clientDetails);
        return "edit_client";
    }

    @PostMapping("/edit_client/{id}")
    public String modifyClient(@PathVariable(value = "id") Long id, @RequestParam String fullName, @RequestParam String email) {

        Client client = new Client();
        client.setId(id);
        client.setFullName(fullName);
        client.setEmail(email);
        clientService.modifyClient(client);
        return "redirect:/clients_list";
    }

    @PostMapping("remove_client/{id}")
    public String removeClient(@PathVariable(value = "id") Long id) {

        clientService.deleteClient(id);
        return "redirect:/clients_list";
    }
}


