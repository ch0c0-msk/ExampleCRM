package com.example.examplecrm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportController {

    @GetMapping("/report_deals")
    public String reportDeals(Model model) {

        model.addAttribute("_new", 250);
        model.addAttribute("in_process", 1000);
        model.addAttribute("success", 300);
        model.addAttribute("failed", 200);
        return "report_deals";
    }

    @GetMapping("/report_users")
    public String reportUsers(Model model) {

        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("user_01", 300);
        data.put("user_02", 250);
        data.put("user_03", 1500);
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());
        return "report_users";
    }

    @GetMapping("/report_week")
    public String reportWeek(Model model) {
        Map<LocalDateTime, Integer> data = new LinkedHashMap<>();
        data.put(LocalDateTime.now().minus(7, ChronoUnit.DAYS),100);
        data.put(LocalDateTime.now().minus(6, ChronoUnit.DAYS),300);
        data.put(LocalDateTime.now().minus(5, ChronoUnit.DAYS),200);
        data.put(LocalDateTime.now().minus(4, ChronoUnit.DAYS),500);
        data.put(LocalDateTime.now().minus(3, ChronoUnit.DAYS),500);
        data.put(LocalDateTime.now().minus(2, ChronoUnit.DAYS),100);
        data.put(LocalDateTime.now().minus(1, ChronoUnit.DAYS),10);
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());
        return "report_week";
    }

    @GetMapping("/report_products")
    public String reportProducts(Model model) {

        Map<String, Integer> dataCount = new LinkedHashMap<String, Integer>();
        dataCount.put("Мобильный тариф 3 в 1", 300);
        dataCount.put("Моноинтернет", 250);
        model.addAttribute("keySet_1", dataCount.keySet());
        model.addAttribute("values_1", dataCount.values());
        Map<String, Integer> dataSum = new LinkedHashMap<String, Integer>();
        dataSum.put("Мобильный тариф 3 в 1", 120000);
        dataSum.put("Моноинтернет", 62500);
        model.addAttribute("keySet_2", dataSum.keySet());
        model.addAttribute("values_2", dataSum.values());
        return "report_products";
    }
}
