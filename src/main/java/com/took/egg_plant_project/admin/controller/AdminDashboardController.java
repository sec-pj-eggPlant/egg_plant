package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.service.AdminDashboardService;
import com.took.egg_plant_project.admin.service.AdminTradeService;
import com.took.egg_plant_project.admin.service.AdminWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final AdminWarehouseService adminWarehouseService;
    private final AdminTradeService adminTradeService;

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("roleStats", adminDashboardService.getRoleStatistics());
        Map<Integer, Long> monthlyDoneTradeCounts = adminTradeService.getMonthlyDoneTradeCounts();
        Map<String, Integer> usageMap = adminWarehouseService.getTodayWarehouseUsageRate();
        model.addAttribute("aUsage", usageMap.get("A"));
        model.addAttribute("bUsage", usageMap.get("B"));
        model.addAttribute("cUsage", usageMap.get("C"));

        long totalMemberCount = adminDashboardService.getTotalMemberCount();
        long totalTrades = adminDashboardService.getTotalTradeCount();
        List<Long> doneCounts = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> monthlyDoneTradeCounts.getOrDefault(i, 0L))
                .collect(Collectors.toList());

        long totalDoneTrades = doneCounts.stream().mapToLong(Long::longValue).sum();
        model.addAttribute("totalDoneTrades", totalDoneTrades);
        model.addAttribute("doneCounts", doneCounts);
        model.addAttribute("totalTradeCount", totalTrades);
        model.addAttribute("totalMemberCount", totalMemberCount);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "admin/dashboard";
    }

}
