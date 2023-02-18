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
    }

}