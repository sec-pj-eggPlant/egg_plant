package com.took.egg_plant_project.member;

import com.took.egg_plant_project.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("로그인 시도: {}", memberDto.getUserID());
        try {
            Member member = memberService.login(memberDto.getUserID(), memberDto.getUserPW());
            session.setAttribute("loginID", member.getUserID());
            redirectAttributes.addFlashAttribute("loginSuccess", member.getNickName() + "님 환영합니다!");;
            return "redirect:/member/login";
        } catch (IllegalArgumentException e) {
            return "redirect:/member/login?error=" + e.getMessage();
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto, RedirectAttributes redirectAttributes) {

        Member member = Member.builder()
                .userID(memberDto.getUserID())
                .userPW(memberDto.getUserPW())
                .userName(memberDto.getUserName())
                .nickName(memberDto.getNickName())
                .userEmail(memberDto.getUserEmail())
                .tel(memberDto.getTel())
                .role(memberDto.getRole())
                .build();

        memberService.signup(member);

        redirectAttributes.addFlashAttribute("signupSuccess", "회원가입이 완료되었습니다!");
        return "redirect:/member/login";
    }

    @GetMapping("/checkUserID")
    @ResponseBody
    public boolean checkUserID(@RequestParam String userID) {
        return memberService.existsByUserID(userID);
    }

    @GetMapping("/checkUserNickname")
    @ResponseBody
    public boolean checkUserNickname(@RequestParam String nickName) {
        return memberService.existsByNickName(nickName);
    }

    @GetMapping("/checkUserEmail")
    @ResponseBody
    public boolean checkUserEmail(@RequestParam String userEmail) {
        return memberService.existsByUserEmail(userEmail);
    }
}
