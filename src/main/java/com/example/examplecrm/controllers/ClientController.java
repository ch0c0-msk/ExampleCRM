package com.example.examplecrm.controllers;

import com.example.examplecrm.exporters.ClientExcelExporter;
import com.example.examplecrm.models.Client;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepo clientRepo;
    private final ClientService clientService;

    @GetMapping("/clients_list")
    public String clientList(Model model, Principal principal) {

        Iterable<Client> clients = clientRepo.findByUser(clientService.getUserByPrincipal(principal));
        model.addAttribute("clients",clients);
        return "clients_list";
    }

//    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/clients_all_list")
    public String clientAllList(Model model) {
        Iterable<Client> clients = clientRepo.findAll();
        model.addAttribute("clients",clients);
        return "clients_list";
    }

    @GetMapping("/add_client")
    public String addClient(Model model) {

        return "add_client";
    }

    @PostMapping("add_client")
    public String createClient(Principal principal, @RequestParam String fullName, @RequestParam String email,
                               @RequestParam String phone, @RequestParam String discount) {

        Client client = new Client();
        client.setFullName(fullName);
        client.setEmail(email);
        client.setPhone(phone);
        try {
            client.setDiscount(Integer.parseInt(discount));
        } catch (NullPointerException npe) {
            client.setDiscount(0);
        } catch (NumberFormatException nfe) {
            client.setDiscount(0);
        }
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
    public String modifyClient(Principal principal, @PathVariable(value = "id") Long id, @RequestParam String fullName, @RequestParam String email) {

        Client client = clientRepo.findById(id).orElse(new Client());
        client.setFullName(fullName);
        client.setEmail(email);
        clientService.modifyClient(client, principal);
        return "redirect:/clients_list";
    }

    @PostMapping("remove_client/{id}")
    public String removeClient(Principal principal, @PathVariable(value = "id") Long id) {

        clientService.deleteClient(id, principal);
        return "redirect:/clients_list";
    }

    @GetMapping("/clients_list/export/excel")
    public String exportClientsList(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clients_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Client> listClients = clientService.getListForExport();

        ClientExcelExporter excelExporter = new ClientExcelExporter(listClients);
        excelExporter.export(response);
        return "redirect:/clients_list";
    }
}


