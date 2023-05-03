package com.example.examplecrm.controllers;

import com.example.examplecrm.exporters.DealExcelExporter;
import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.Product;
import com.example.examplecrm.models.User;
import com.example.examplecrm.repos.ClientRepo;
import com.example.examplecrm.repos.DealRepo;
import com.example.examplecrm.repos.ProductRepo;
import com.example.examplecrm.repos.UserRepo;
import com.example.examplecrm.services.DealService;
import com.example.examplecrm.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DealController {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final DealRepo dealRepo;
    private final ClientRepo clientRepo;
    private final DealService dealService;
    private final EmailService emailService;

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
        return "redirect:/deals_list";
    }

    @GetMapping("/deals_list")
    public String myDealList(Principal principal, Model model) {

        User user = userRepo.findByLogin(principal.getName());
        Iterable<Deal> deals = dealRepo.findByUser(user);
        model.addAttribute("deals",deals);
        return "deals_list";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/deals_all_list")
    public String allDealList(Principal principal, Model model) {

        Iterable<Deal> deals = dealRepo.findAll();
        model.addAttribute("deals",deals);
        return "deals_list";
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
        return "redirect:/deals_list";
    }

    @PostMapping("/remove_deal/{id}")
    public String removeDeal(@PathVariable(value = "id") Long id, Principal principal) {

        dealService.deleteDeal(principal, id);
        return "redirect:/deals_list";
    }

    @GetMapping("/deals_list/export/excel")
    public String exportDealsList(HttpServletResponse response, Principal principal) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=active_deals_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        dealService.setDealStatus(dealService.getListForExport(principal));
        List<Deal> listDeals = dealService.getListForExport(principal);

        DealExcelExporter excelExporter = new DealExcelExporter(listDeals);
        excelExporter.export(response);
        return "redirect:/deals_list";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/deals_all_list/export/excel")
    public String exportAllDealsList(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=all_active_deals_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        dealService.setDealStatus(dealService.getListAllForExport());
        List<Deal> listDeals = dealService.getListAllForExport();

        DealExcelExporter excelExporter = new DealExcelExporter(listDeals);
        excelExporter.export(response);
        return "redirect:/deals_all_list";
    }

    @PostMapping("/deals_list/import/excel")
    public String importDealsList(@RequestParam MultipartFile file, Principal principal) throws IOException {
        dealService.updateDealStatus(file, principal);
        return "redirect:/deals_list";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/deals_all_list/import/excel")
    public String importAllDealsList(@RequestParam MultipartFile file, Principal principal) throws IOException {
        dealService.updateDealStatus(file, principal);
        return "redirect:/deals_all_list";
    }

    @GetMapping("/send_email")
    public String sendEmail(Model model, Principal principal) {

        User user = userRepo.findByLogin(principal.getName());
        Iterable<Client> clients = clientRepo.findByUser(user);
        model.addAttribute("clients",clients);
        Iterable<Product> products = productRepo.findAll();
        model.addAttribute("products",products);
        return "send_email";
    }

    @PostMapping("/send_email")
    public String sendEmailOffer(Principal principal, @RequestParam Long client_id, @RequestParam Long product_id) {

        Deal deal = new Deal();
        deal.setClient(clientRepo.findById(client_id).orElse(null));
        deal.setProduct(productRepo.findById(product_id).orElse(null));
        dealService.sendClientOffer(deal, principal);
        return "redirect:/deals_list";
    }
}
