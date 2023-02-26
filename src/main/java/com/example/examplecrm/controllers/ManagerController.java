package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Product;
import com.example.examplecrm.repos.ProductRepo;
import com.example.examplecrm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAuthority('MANAGER')")
@RequiredArgsConstructor
public class ManagerController {

    private final ProductRepo productRepo;
    private final ProductService productService;

    @GetMapping("/add_product")
    public String addProduct(Model model) {

        return "add_product";
    }

    @PostMapping("add_product")
    public String createProduct(Principal principal, @RequestParam String name, @RequestParam String price) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        productService.createProduct(principal, product);
        return "redirect:/products_list";
    }

    @GetMapping("/edit_product/{id}")
    public String editClient(@PathVariable(value = "id") Long id, Model model) {

        Optional<Product> product = productRepo.findById(id);
        ArrayList<Product> productDetails = new ArrayList<>();
        product.ifPresent(productDetails::add);
        model.addAttribute("products",productDetails);
        return "edit_product";
    }

    @PostMapping("/edit_product/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam String price) {

        Product product = productRepo.findById(id).orElse(null);
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        productService.modifyProduct(product);
        return "redirect:/products_list";
    }

    @PostMapping("remove_product/{id}")
    public String removeClient(@PathVariable(value = "id") Long id) {

        productService.deleteProduct(id);
        return "redirect:/products_list";
    }
}
