package com.egg_plant_project.member;

import com.egg_plant_project.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberDao memberDao;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, HttpSession session) {
        log.info("로그인 시도: {}", memberDto.getUserID());


        Member member = memberDao.findByUserID(memberDto.getUserID())
                .orElse(null);

        if (member == null) {
            return "redirect:/member/login?error=notfound";
        }

        if (!member.getUserPW().equals(memberDto.getUserPW())) {
            return "redirect:/member/login?error=wrongpw";
        }

        session.setAttribute("loginID", member.getUserID());
        return "redirect:/main/list";   // 메인 리스트 페이지
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto, HttpSession session) {

        String encodedPw = bCryptPasswordEncoder.encode(memberDto.getUserPW());

        Member member = Member.builder()
                .userID(memberDto.getUserID())
                .userPW(encodedPw)
                .userName(memberDto.getUserName())
                .nickName(memberDto.getNickName())
                .userEmail(memberDto.getUserEmail())
                .tel(memberDto.getTel())
                .role(memberDto.getRole())
                .build();

        memberDao.save(member);

        return "redirect:/member/login";
    }
}
