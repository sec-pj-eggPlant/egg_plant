package com.took.egg_plant_project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminMemberDetailController {

    @GetMapping("/admin/member_detail/{memberId}")
    public String showMemberDetail(@PathVariable("memberId") Long memberId, Model model) {
        // 추후 DB에서 memberId로 회원 정보를 조회하여 모델에 담을 수 있습니다.
        model.addAttribute("memberId", memberId);
        return "admin/member_detail";  // templates/admin/member_detail.html or views/admin/member_detail.jsp
    }
}