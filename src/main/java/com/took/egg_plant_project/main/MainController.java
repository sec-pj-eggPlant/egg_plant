package com.took.egg_plant_project.main;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String status,
                       HttpSession session,
                       Model model) {
        Object loginSuccess = session.getAttribute("loginSuccessMessage");
        if (loginSuccess != null) {
            model.addAttribute("loginSuccessMessage", loginSuccess);
            session.removeAttribute("loginSuccessMessage");
        }

        List<Post> posts = (status == null || status.isEmpty())
                ? mainService.getAllPosts()
                : mainService.getPostByStatus(status);
        model.addAttribute("posts", posts);

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

