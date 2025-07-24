package com.took.egg_plant_project.main;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Post;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/list")
    public String getList(@RequestParam(value = "role", required = false) String role,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(required = false) Integer price,
                          @RequestParam(required = false) String location,
                          @RequestParam(required = false) Integer area,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                          @RequestParam(required = false) String keyword,
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
        Pageable pageable = PageRequest.of(page, 4);

        Page<MainDto> postsPage = mainService.filterPostsByConditions(
                targetRole, price, location, area, startDate, endDate, keyword, pageable);

        //Page<MainDto> postsPage = mainService.getPagedPosts(targetRole, pageable);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());

        // ✅ 검색 조건 유지
        model.addAttribute("role", role);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("area", area);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("keyword", keyword);

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
                             @RequestParam(required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "reset", required = false) Boolean reset,
                             Model model) {

        if (Boolean.TRUE.equals(reset)) {
            return "redirect:/main/list?role=" + role;
        }

        if (location != null && location.trim().isEmpty()) location = null;
        if (keyword != null && keyword.trim().isEmpty()) keyword = null;

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            startDate = null;
            endDate = null;
        }

        Role targetRole = Role.valueOf("ROLE_" + role);
        Pageable pageable = PageRequest.of(page, 4); // 페이지당 4개

        Page<MainDto> postsPage = mainService.filterPostsByConditions(
                targetRole, price, location, area, startDate, endDate, keyword, pageable);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());

        // 검색 조건 유지
        model.addAttribute("role", role);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("area", area);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
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

        String roleParam = writer.getRole() == Role.ROLE_OWNER ? "OWNER" : "RENTER";
        return "redirect:/main/list?role=" + roleParam;
    }

    @GetMapping("/detail/{id}")
    public String goDetailPage(@PathVariable Integer id,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {
        Post post = mainService.getPostById(id);
        Integer userId = userDetails.getLoggedMember().getId();

        MainDto dto = new MainDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPrice(post.getPrice());
        dto.setArea(post.getArea());
        dto.setLocation(post.getLocation());
        dto.setStartDate(post.getStartDate());
        dto.setEndDate(post.getEndDate());
        dto.setStatus(post.getStatus());
        dto.setImagePath(post.getImagePath());
        dto.setWriterRole(post.getWriter().getRole().name());
        dto.setLatitude(post.getLatitude());
        dto.setLongitude(post.getLongitude());
        dto.setWriterId(post.getWriter().getId());

        model.addAttribute("post", dto);
        model.addAttribute("userId", userId);

        if ("IN_PROGRESS".equals(post.getStatus())) {
            model.addAttribute("trade", mainService.getTradeByPostId(id));
        }

        return "main/detail";
    }

    @PostMapping("/post/{id}/status")
    public String updatePostStatus(
            @PathVariable Integer id,
            @RequestParam String action,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        Post post = mainService.getPostById(id);
        boolean isWriter = post.getWriter().getId().equals(userDetails.getLoggedMember().getId());
        Role writerRole = post.getWriter().getRole();

        String newStatus = post.getStatus();

        Member currentUser = userDetails.getLoggedMember();

        switch (action) {
            case "apply" -> mainService.applyTrade(post, currentUser);

            case "cancel" -> {
                var trade = mainService.getTradeByPostId(post.getId());
                boolean isApplicant = trade != null && trade.getRenter().getId().equals(currentUser.getId());

                if (isWriter || isApplicant) {
                    mainService.cancelTrade(post);
                } else {
                    return "redirect:/main/detail/" + id; // 권한 없음
                }
            }

            case "complete" -> {
                if (!isWriter) return "redirect:/main/detail/" + id;
                mainService.completeTrade(post);
            }
        }

        redirectAttributes.addAttribute("role", writerRole == Role.ROLE_OWNER ? "OWNER" : "RENTER");
        return "redirect:/main/list";
    }
}

