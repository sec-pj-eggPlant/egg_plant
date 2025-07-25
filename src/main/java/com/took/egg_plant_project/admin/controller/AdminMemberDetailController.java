package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.service.AdminMemberDetailService;
import com.took.egg_plant_project.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberDetailController {

    private final AdminMemberDetailService adminMemberDetailService;

    @GetMapping("/admin/member/detail")
    public String showMemberPosts(@RequestParam("userId") String userId, Model model) {
        List<Post> postList = adminMemberDetailService.getPostsByUserId(userId);
        model.addAttribute("postList", postList);
        model.addAttribute("userId", userId);
        return "admin/member/detail";
    }
}