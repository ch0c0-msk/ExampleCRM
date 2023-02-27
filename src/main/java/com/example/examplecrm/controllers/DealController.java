package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.Product;
import com.example.examplecrm.models.User;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.repos.DealRepo;
import com.example.examplecrm.repos.ProductRepo;
import com.example.examplecrm.repos.UserRepo;
import com.example.examplecrm.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DealController {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final DealRepo dealRepo;
    private final ClientRepo clientRepo;
    private final DealService dealService;

    @GetMapping("/add_deal")
    public String addDeal(Model model, Principal principal) {

        User user = userRepo.findByLogin(principal.getName());
        Iterable<Client> clients = clientRepo.findByUser(user);
        model.addAttribute("clients",clients);
        Iterable<Product> products = productRepo.findAll();
        model.addAttribute("products",products);
        return "add_deal";
    }

    @PostMapping("add_deal")
    public String createDeal(Principal principal, @RequestParam Long client_id, @RequestParam Long product_id) {

        Deal deal = new Deal();
        deal.setClient(clientRepo.findById(client_id).orElse(null));
        deal.setProduct(productRepo.findById(product_id).orElse(null));
        dealService.createDeal(principal, deal);
        return "redirect:/my_deals";
    }

    @GetMapping("/my_deals")
    public String myDealList(Principal principal, Model model) {

        User user = userRepo.findByLogin(principal.getName());
        Iterable<Deal> deals = dealRepo.findByUser(user);
        model.addAttribute("deals",deals);
        return "my_deals_list";
    }

    @GetMapping("/edit_deal/{id}")
    public String editDeal(@PathVariable(value = "id") Long id, Model model) {

        Deal deal = dealRepo.findById(id).orElse(null);
        ArrayList<Deal> deals = new ArrayList<>();
        deals.add(deal);
        model.addAttribute("deals", deals);
        return "edit_deal";
    }

    @PostMapping("/edit_deal/{id}")
    public String modifyDeal(@PathVariable(value = "id") Long id, @RequestParam String status, Principal principal) {

        Deal deal = dealRepo.findById(id).orElse(new Deal());
        deal.setStatus(status);
        deal.setUpdateDate(LocalDateTime.now());
        dealService.modifyDeal(principal, deal);
        return "redirect:/my_deals";
    }

    @PostMapping("/remove_deal/{id}")
    public String removeDeal(@PathVariable(value = "id") Long id, Principal principal) {

        dealService.deleteDeal(principal, id);
        return "redirect:/my_deals";
    }
}
