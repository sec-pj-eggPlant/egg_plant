package com.took.egg_plant_project.main;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("")
    public String redirectByUserRole(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            Role userRole = userDetails.getLoggedMember().getRole();

            if (userRole == Role.ROLE_RENTER) {
                return "redirect:/main/list?role=RENTER"; //OWNER
            } else if (userRole == Role.ROLE_OWNER) {
                return "redirect:/main/list?role=OWNER"; //RENTER
            }
        }
        return "redirect:/member/login";
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String status,
                       @RequestParam(required = false) String location,
                       @RequestParam(required = false) Integer price,
                       @RequestParam(required = false) Integer area,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String role,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       HttpSession session,
                       Model model) {

        if ((role == null || role.isBlank()) && userDetails != null) {
            Role userRole = userDetails.getLoggedMember().getRole();
            role = (userRole == Role.ROLE_OWNER) ? "OWNER" : "RENTER";
        }

        Role roleEnum = null;
        if (role != null && !role.isBlank()) {
            try {
                roleEnum = Role.valueOf("ROLE_" + role.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        List<Post> posts = mainService.getFilteredPosts(status, location, price, area, startDate, endDate, keyword, roleEnum);
        List<Post> posts2 = mainService.getAllPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("role", role);
        model.addAttribute("status", status);
        model.addAttribute("location", location);
        model.addAttribute("price", price);
        model.addAttribute("keyword", keyword);

        Object loginSuccess = session.getAttribute("loginSuccessMessage");
        if (loginSuccess != null) {
            model.addAttribute("loginSuccessMessage", loginSuccess);
            session.removeAttribute("loginSuccessMessage");
        }

        return "main/list";
    }




    @GetMapping("/write")
    public String writeForm() {
        return "main/write";
    }

    @PostMapping("/write")
    public String savePost(@ModelAttribute MainDto dto,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member writer = userDetails.getLoggedMember();
        mainService.savePost(dto, writer);
        return "redirect:/main/list";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Integer id, Model model) {
        Post post = mainService.getPostById(id);
        if (post == null) return "redirect:/main/list";

        model.addAttribute("post", post);
        return "main/detail";
    }
}

