package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberDto;
import com.took.egg_plant_project.mypage.dao.MypageDao;
import com.took.egg_plant_project.mypage.dto.MypageUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MypageService {
    private final MypageDao mypageDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberDto findByUserID(String userID) {
        return mypageDao.findByUserID(userID)
                .map(Member::toMemberDto)
                .orElse(null);
    }

    public void updateInfo(MemberDto memberDto) {
        Member member = mypageDao.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        member.updateInfo(
                memberDto.getUserPW(),
                memberDto.getUserEmail(),
                memberDto.getTel()
        );
    }
}
