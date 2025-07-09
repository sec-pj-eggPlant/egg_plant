package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("roleStats", adminDashboardService.getRoleStatistics());

        long totalMemberCount = adminDashboardService.getTotalMemberCount();
        model.addAttribute("totalMemberCount", totalMemberCount);
        return "admin/dashboard";
    }
}
