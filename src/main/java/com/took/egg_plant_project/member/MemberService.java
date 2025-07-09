package com.took.egg_plant_project.member;

import com.took.egg_plant_project.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(Member member) {
        if (memberRepository.existsByUserID(member.getUserID())) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }
        if (memberRepository.existsByUserEmail(member.getUserEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
        if (memberRepository.existsByNickName(member.getNickName())) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }

        String encodedPw = passwordEncoder.encode(member.getUserPW());

        member = Member.builder()
                .userID(member.getUserID())
                .userPW(encodedPw)
                .nickName(member.getNickName())
                .userName(member.getUserName())
                .userEmail(member.getUserEmail())
                .tel(member.getTel())
                .role(member.getRole())
                .build();

        memberRepository.save(member);
    }

    public Member login(String userID, String rawPassword) {
        Member member = memberRepository.findByUserID(userID)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        if (!passwordEncoder.matches(rawPassword, member.getUserPW())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    public boolean existsByUserID(String userID) {
        return memberRepository.existsByUserID(userID);
    }

    public boolean existsByUserEmail(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }

    public boolean existsByNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }
}
