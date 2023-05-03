package com.example.examplecrm.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser("admin")
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReturnClientsList() throws Exception {
        Assertions.assertEquals(1,1);
        mockMvc.perform(get("/clients_list")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<table class=\"table table-striped table-bordered\">")));
    }

    @Test
    public void shouldReturnAddClientPage() throws Exception {
        Assertions.assertEquals(1,1);
        mockMvc.perform(get("/add_client")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<form method=\"post\">")));
    }

    @Test
    public void shouldLogOut() throws Exception {
        Assertions.assertEquals(1,1);
        mockMvc.perform(post("/logout")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldLogIn() throws Exception {

        RequestBuilder requestBuilder = post("/login")
                .param("username", "admin")
                .param("password", "admin");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
