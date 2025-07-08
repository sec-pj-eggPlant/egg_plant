package com.egg_plant_project.main;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@Slf4j
public class MainController {

    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        Object loginSuccess = session.getAttribute("loginSuccessMessage");
        if (loginSuccess != null) {
            model.addAttribute("loginSuccessMessage", loginSuccess);
            session.removeAttribute("loginSuccessMessage");
        }
        return "main/list";
    }
}
