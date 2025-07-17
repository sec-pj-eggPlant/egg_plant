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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/list")
    public String getList(@RequestParam(value = "role", required = false) String role,
                          @AuthenticationPrincipal CustomUserDetails userDetails,
                          HttpSession session,
                          Model model) {
        // 로그인 환영 메시지
        Object loginSuccess = session.getAttribute("loginSuccessMessage");
        if (loginSuccess != null) {
            model.addAttribute("loginSuccessMessage", loginSuccess);
            session.removeAttribute("loginSuccessMessage");
        }

        // role 파라미터가 없으면 로그인한 사용자의 반대 role을 기본값으로 사용
        if (role == null && userDetails != null) {
            Role loginRole = userDetails.getLoggedMember().getRole();
            role = (loginRole == Role.ROLE_OWNER) ? "RENTER" : "OWNER";
        }

        Role targetRole = Role.valueOf("ROLE_" + role);
        List<MainDto> posts = mainService.getFilteredPosts(targetRole);

        model.addAttribute("role", role);
        model.addAttribute("posts", posts);
        model.addAttribute("price", null);
        model.addAttribute("location", null);
        model.addAttribute("area", null);
        model.addAttribute("startDate", null);
        model.addAttribute("endDate", null);
        model.addAttribute("status", null);
        model.addAttribute("keyword", null);

        return "main/list";
    }

    @GetMapping("")
    public String redirectByUserRole(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            Role userRole = userDetails.getLoggedMember().getRole();
            // 로그인한 사람과 반대 역할의 리스트 보여주기
            if (userRole == Role.ROLE_RENTER) {
                return "redirect:/main/list?role=OWNER";
            } else if (userRole == Role.ROLE_OWNER) {
                return "redirect:/main/list?role=RENTER";
            }
        }
        return "redirect:/main/list"; // 기본 리스트
    }

    @PostMapping("/list")
    public String filterList(@RequestParam(value = "role", required = false) String role,
                             @RequestParam(required = false) Integer price,
                             @RequestParam(required = false) String location,
                             @RequestParam(required = false) Integer area,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String keyword,
                             Model model) {

        if (location != null && location.trim().isEmpty()) location = null;
        if (status != null && status.trim().isEmpty()) status = null;
        if (keyword != null && keyword.trim().isEmpty()) keyword = null;


        Role targetRole = Role.valueOf("ROLE_" + role);

        List<MainDto> posts = mainService.filterPostsByConditions(targetRole, price, location, area, startDate, endDate, status, keyword);

        model.addAttribute("role", role);
        model.addAttribute("posts", posts);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("area", area);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        return "main/list";
    }

    @GetMapping("/write")
    public String goWritePage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/member/login";
        }

        Role userRole = userDetails.getLoggedMember().getRole();
        model.addAttribute("role", userRole.name());
        return "/main/write"; // write.html 렌더링
    }

    @PostMapping("/write")
    public String savePost(@ModelAttribute MainDto dto,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        Member writer = userDetails.getLoggedMember();
        mainService.savePostWithImage(dto, writer, imageFile);
        return "redirect:/main/list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model) {
        Post post = mainService.getPostById(id);
        if (post == null) return "redirect:/main/list";
        model.addAttribute("post", post);
        return "main/detail";
    }
}

