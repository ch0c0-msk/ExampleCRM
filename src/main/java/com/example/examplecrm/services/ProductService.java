package com.example.examplecrm.services;

import com.example.examplecrm.models.Product;
import com.example.examplecrm.models.User;
import com.example.examplecrm.repos.ProductRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public boolean createProduct(Principal principal, Product product) {
        product.setUser(getUserByPrincipal(principal));
        productRepo.save(product);
        log.info("Saving new product with attributes: {}",product.toString());
        return true;
    }

    public boolean modifyProduct(Product product) {

        if (productRepo.findById(product.getId()) == null) {
            log.info("Product with id: {} doesnt exist", product.getId());
            return false;
        } else {
            productRepo.save(product);
            log.info("Modify product with new attributes: {}",product.toString());
            return true;
        }
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            log.info("Product with id: {} doesnt exist", id);
            return false;
        } else {
            productRepo.delete(product);
            log.info("Product with attributes: {} was deleted", product.toString());
            return true;
        }
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) { return new User(); }
        return userRepo.findByLogin(principal.getName());
    }

}
