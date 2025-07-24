package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.repository.AdminTradeRepository;
import com.took.egg_plant_project.admin.service.AdminMemberService;
import com.took.egg_plant_project.admin.service.AdminTradeService;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTradeController {

    private final AdminTradeService adminTradeService;
    private final AdminMemberService adminMemberService;

    @GetMapping("/admin/trade")
    public String showAllTrades(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Trade> tradeList = adminTradeService.searchByCondition(category, keyword, startDate, endDate, pageable);

        model.addAttribute("tradeList", tradeList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        List<Member> renterList = adminMemberService.findByRole(Role.ROLE_RENTER);
        List<Member> ownerList = adminMemberService.findByRole(Role.ROLE_OWNER);
        model.addAttribute("renterList", renterList);
        model.addAttribute("ownerList", ownerList);

        return "admin/trade";
    }
}