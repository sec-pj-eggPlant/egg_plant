package com.took.egg_plant_project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminTradesController {

    @GetMapping("/admin/trades")
    public String showTradeListPage() {
        return "admin/trades";  // templates/admin/trades.html 또는 views/admin/trades.jsp
    }
}