package com.took.egg_plant_project.mypage.controller;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.member.MemberDto;
import com.took.egg_plant_project.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String userID = customUserDetails.getUsername();
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);
        log.info("loggedMemberDto: {}", loggedMemberDto);

        model.addAttribute("loggedMemberDto", loggedMemberDto);
        return "mypage/mypage";
    }

    @GetMapping("/modify")
    public String modify(){
        return "mypage/modify";
    }
}
