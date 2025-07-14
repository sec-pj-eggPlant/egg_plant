package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.service.AdminMemberService;
import com.took.egg_plant_project.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/admin/member")
    public String showMembers(@RequestParam(required = false) String category,
                              @RequestParam(required = false) String keyword,
                              Model model) {
        List<Member> memberList = adminMemberService.searchMembers(category, keyword);
        model.addAttribute("memberList", memberList);
        return "admin/member";
    }


}