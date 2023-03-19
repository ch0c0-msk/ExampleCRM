package com.example.examplecrm.services;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendClientOffer(Deal deal) throws Exception {

        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        Context context = new Context();
        context.setVariable("name",deal.getClient().getFullName());
        context.setVariable("product",deal.getProduct().getName());
        Double price = deal.getProduct().getPrice() * (1 - (double)deal.getClient().getDiscount() / 100);
        context.setVariable("price", price);
        context.setVariable("phone", deal.getCreateUser().getPhone());
        context.setVariable("email", deal.getCreateUser().getEmail());
        messageHelper.setFrom("example.crm.bmstu@gmail.com");
        messageHelper.setTo(deal.getClient().getEmail());
        messageHelper.setSubject("Отправлено из Example CRM");
        messageHelper.setText(templateEngine.process("email_client_offer",context),true);
        mailSender.send(mailMessage);
        log.info("Success send email to {}",deal.getClient().getEmail());
    }

}
