package com.example.examplecrm.controllers;

import com.example.examplecrm.models.Message;
import com.example.examplecrm.repos.MessageRepo;
import com.example.examplecrm.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepo messageRepo;
    private final MessageService messageService;

    @GetMapping("/support_send")
    public String supportSend() {

        return "support_send";
    }

    @PostMapping ("/support_send")
    public String supportCreate(Principal principal, @RequestParam String label, @RequestParam String importance, @RequestParam String text) {

        Message message = new Message();
        message.setLabel(label);
        message.setImportance(importance);
        message.setText(text);
        messageService.createMessage(message, principal);
        return "redirect:/message_list";
    }

    @GetMapping("/message_list")
    public String messageList(Model model, Principal principal) {

        Iterable<Message> messages = messageRepo.findByUser(messageService.getUserByPrincipal(principal));
        model.addAttribute("messages",messages);
        return "message_list";
    }
}
