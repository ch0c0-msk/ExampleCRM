package com.example.examplecrm.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/add_user").setViewName("add_user");
        registry.addViewController("/clients_list").setViewName("clients_list");
        registry.addViewController("/add_client").setViewName("add_client");
        registry.addViewController("/users_list").setViewName("users_list");
        registry.addViewController("/edit_client").setViewName("edit_client");
        registry.addViewController("/add_product").setViewName("add_product");
        registry.addViewController("/products_list").setViewName("products_list");
        registry.addViewController("/edit_product").setViewName("edit_product");
        registry.addViewController("/add_deal").setViewName("add_deal");
        registry.addViewController("/deals_list").setViewName("deals_list");
    }

}