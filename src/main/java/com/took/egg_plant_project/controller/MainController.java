package com.took.egg_plant_project.controller;

import com.took.egg_plant_project.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("main")
@Slf4j
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "main/login";
    }
}
