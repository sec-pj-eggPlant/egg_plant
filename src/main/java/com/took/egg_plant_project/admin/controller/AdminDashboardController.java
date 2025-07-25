package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.service.AdminDashboardService;
import com.took.egg_plant_project.admin.service.AdminWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final AdminWarehouseService adminWarehouseService;

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("roleStats", adminDashboardService.getRoleStatistics());
        Map<String, Integer> usageMap = adminWarehouseService.getTodayWarehouseUsageRate();
        model.addAttribute("aUsage", usageMap.get("A"));
        model.addAttribute("bUsage", usageMap.get("B"));
        model.addAttribute("cUsage", usageMap.get("C"));

        long totalMemberCount = adminDashboardService.getTotalMemberCount();
        model.addAttribute("totalMemberCount", totalMemberCount);
        return "admin/dashboard";
    }

}
