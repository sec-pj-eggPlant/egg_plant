package com.took.egg_plant_project.mypage.controller;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberDto;
import com.took.egg_plant_project.mypage.dto.MypageModifyDto;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my")
public class MypageController {

    private final MypageModifyService mypageModifyService;
    private final MypageTradesService mypageTradesService;
    private final MypagePostService mypagePostService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final MyChatService myChatService;

    @GetMapping("/my")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        String userID = customUserDetails.getUsername();
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();
        Member loggedMemberDto = mypageModifyService.findByUserID(userID);
        log.info("loggedMemberDto: {}", loggedMemberDto);

        model.addAttribute("loggedMemberDto", loggedMemberDto);
        log.info(loggedMemberDto.toString());

        return "my/my";
    }

    //회원 정보 수정
    @GetMapping("/mypage-profile")
    public String modifyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        if (customUserDetails == null) {
            log.warn("로그인 정보가 없습니다. 로그인 페이지로 이동합니다.");
            return "redirect:/member/login"; // 로그인 페이지 경로
        }

        String userID = customUserDetails.getUsername();
        Member member = mypageModifyService.findByUserID(userID);
        model.addAttribute("user", member);
        return "my/mypage-profile";
    }

    //회원 정보 수정
    @GetMapping("/modify")
    public String profileEdit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = mypageModifyService.findByUserID(userDetails.getUsername());
        model.addAttribute("member", member);
        return "my/modify";
    }


    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public Member info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        Member loggedMemberDto = mypageModifyService.findByUserID(userID); //로그인한 유저의 정보들

        return loggedMemberDto;
    }



    @PostMapping("/modify")
    @ResponseBody
    public Map<String, String> modify(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MypageModifyDto data) {

        Map<String, String> result = new HashMap<>();

        if (customUserDetails == null) {
            result.put("isModify", "false");
            result.put("error", "로그인 정보가 없습니다.");
            return result;
        }

        try {
            String userID = customUserDetails.getUsername();
            Member member = mypageModifyService.findByUserID(userID);

            // 1. 현재 비밀번호 입력 여부 체크
            if (data.getCurrentPW() == null || data.getCurrentPW().isBlank()) {
                result.put("isModify", "false");
                result.put("error", "비밀번호를 입력해주세요.");
                return result;
            }

            // 2. 현재 비밀번호 일치 여부 확인
            if (!bCryptPasswordEncoder.matches(data.getCurrentPW(), member.getUserPW())) {
                result.put("isModify", "false");
                result.put("error", "현재 비밀번호가 올바르지 않습니다.");
                return result;
            }

            // 3. 새 비밀번호와 확인 비밀번호가 일치하는지 체크 (비밀번호 변경 원할 시)
            if (data.getNewPW() != null && !data.getNewPW().isBlank()) {
                if (!data.getNewPW().equals(data.getConfirmPW())) {
                    result.put("isModify", "false");
                    result.put("error", "비밀번호 확인이 일치하지 않습니다.");
                    return result;
                }
            }

            // 4. 서비스 호출 (멤버 엔티티와 DTO 전달)
            mypageModifyService.modifyMemberInfo(member, data);

            result.put("isModify", "true");
        } catch (IllegalArgumentException e) {
            result.put("isModify", "false");
            result.put("error", e.getMessage());
        } catch (Exception e) {
            log.error("회원정보 수정 중 오류 발생", e);
            result.put("isModify", "false");
            result.put("error", "서버 오류가 발생했습니다.");
        }

        return result;
    }

    //거래 내역 조회
    @GetMapping("/trades")
    public String trades(
                        @AuthenticationPrincipal CustomUserDetails customUserDetails,
                         @RequestParam(required = false) String searchType,
                         @RequestParam(required = false) String keyword,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                         Model model) {
        if (customUserDetails == null) {
            return "redirect:/member/login"; // 로그인 안된 경우 로그인 페이지로
        }

        Integer memberId = customUserDetails.getLoggedMember().getId();

        int totalCount = mypageTradesService.countTrades(memberId, searchType, keyword, startDate, endDate);
        model.addAttribute("totalCount", totalCount);

        List<MypageTradesDto> tradesList = mypageTradesService.getTrades(
                 memberId, searchType, keyword, startDate, endDate, page, pageSize);

        model.addAttribute("tradesList", tradesList);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "my/trades";
    }

    //내가 쓴 게시글 목록
    @GetMapping("/post")
    public String post(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int pageSize) {
        if (customUserDetails == null) {
            return "redirect:/member/login";
        }
        Integer memberId = customUserDetails.getLoggedMember().getId();
        Page<MypagePostDto> postPage = mypagePostService.getMyPosts(memberId,  page, pageSize);

        model.addAttribute("postPage", postPage);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "my/post";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Integer id, Model model) {
        log.info("id===={}",id);
        MypagePostDto post = mypagePostService.getPostById(id);
        model.addAttribute("post", post);
        log.info("post===={}",post.toString());
        return "my/post-detail";
    }


}
