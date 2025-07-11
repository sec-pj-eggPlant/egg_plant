package com.took.egg_plant_project.mypage.controller;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.member.MemberDto;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.service.MypageService;
import com.took.egg_plant_project.mypage.service.MypageTradesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my")
public class MypageController {
    private final MypageService mypageService;
    private final MypageTradesService mypageTradesService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/my")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String userID = customUserDetails.getUsername();
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);
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
        MemberDto memberDto = mypageService.findByUserID(userID);
        model.addAttribute("user", memberDto);
        return "my/mypage-profile";
    }

    //회원 정보 수정
    @GetMapping("/modify")
    public String profileEdit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberDto member = mypageService.findByUserID(userDetails.getUsername());
        model.addAttribute("member", member);
        return "my/modify";
    }


    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public MemberDto info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들

        return loggedMemberDto;
    }



    @PostMapping("/modify")
    @ResponseBody
    public Map<String, String> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @RequestBody Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        String currentPassword = data.get("currentPassword");
        String password = data.get("password");
        String password2 = data.get("password2");
        String newEmail = data.get("userEmail");
        String newTel = data.get("tel");

        String userID = customUserDetails.getUsername();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);

        log.info("loggedMemberDto: {}", loggedMemberDto);
        log.info("currentPassword: {}", currentPassword);
        log.info("data: {}", newEmail);
        log.info("data: {}", newTel);
        log.info("data: {}", userID);
        log.info("password: {}", password);
        log.info("password2: {}", password2);
        log.info("currentPassword != null: {}", currentPassword != null);
        log.info("currentPassword.isBlank(): {}", !currentPassword.isBlank());
        if (currentPassword == null || currentPassword.isBlank()) {
            result.put("isModify", "false");
            result.put("error", "비밀번호를 입력해주세요");
            return result;
        }
        if (currentPassword != null && !currentPassword.isBlank()) {
            if (!bCryptPasswordEncoder.matches(currentPassword, loggedMemberDto.getUserPW())) {
                log.info("첫번째");
                result.put("isModify", "false");
                result.put("error", "현재 비밃번호가 올바르지 않습니다.");
                return result;
            }

            if (!password.equals(password2)) {
                result.put("isModify", "false");
                result.put("error", "비밀번호 확인이 일치하지 않습니다.");
                return result;
            }

            String encodeUserPW = bCryptPasswordEncoder.encode(password);
            loggedMemberDto.setUserPW(encodeUserPW);
        } else {
            log.info("두번째");
            // 비밀번호 변경 안 하는 경우 기존 비밀번호 유지
            loggedMemberDto.setUserPW(customUserDetails.getPassword());
        }

        if (newEmail != null) loggedMemberDto.setUserEmail(newEmail);
        if (newTel != null) loggedMemberDto.setTel(newTel);
        log.info("modify_loggedMemberDto: {}", loggedMemberDto);
        mypageService.updateInfo(loggedMemberDto);
        result.put("isModify", "true");
        return result;
    }

    //거래 내역 조회
    @GetMapping("/trades2")
    public String trades(@RequestParam(required = false) String status,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                          @RequestParam(required = false, defaultValue = "1") int page,
                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                          Model model) {
        //int totalCount = mypageTradesService.countTrades(category, keyword, startDate, endDate, status);
        //model.addAttribute("totalCount", totalCount);
        List<MypageTradesDto> tradesList = mypageTradesService.searchTrades(status, startDate, endDate, page, pageSize);

        model.addAttribute("tradesList", tradesList);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "my/trades";
    }

    //나의 채팅내역
    @GetMapping("/chat")
    public String chat() {
        return "my/chat";
    }

    //내가 쓴 게시글 목록
    @GetMapping("/mywrite-list")
    public String myWriteList() {
        return "my/mywrite-list";
    }


}
