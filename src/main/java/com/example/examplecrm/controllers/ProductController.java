package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Product;
import com.example.examplecrm.repos.ProductRepo;
import com.example.examplecrm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepo productRepo;

    @GetMapping("/products_list")
    public String productList(Model model) {

        Iterable<Product> products = productRepo.findAll();
        model.addAttribute("products",products);
        return "products_list";
    }

}
