package com.egg_plant_project.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}
