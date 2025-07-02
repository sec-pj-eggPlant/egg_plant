package com.took.egg_plant_project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMemberController {

    @GetMapping("/admin/member")
    public String showMemberPage() {
        return "admin/member";  // templates/admin/member.html or views/admin/member.jsp
    }
}