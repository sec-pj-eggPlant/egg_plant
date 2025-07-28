package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberDto;
import com.took.egg_plant_project.mypage.dao.MypageModifyDao;
import com.took.egg_plant_project.mypage.dto.MypageModifyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageModifyService {

    private final MypageModifyDao mypageModifyDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Member findByUserID(String userID) {
        return mypageModifyDao.findByUserID(userID)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 현재 비밀번호가 맞는지 검증
     */
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 회원 정보 수정 처리
     */
    @Transactional
    public void modifyMemberInfo(Member member, MypageModifyDto dto) {
        log.info("modifyMemberInfo 호출 - id={}, userEmail={}, tel={}", member.getId(), dto.getUserEmail(), dto.getTel());

        // 1. 새 비밀번호 변경 유무 확인 및 검증
        if (dto.getNewPW() != null && !dto.getNewPW().isBlank()) {
            if (!dto.getNewPW().equals(dto.getConfirmPW())) {
                throw new IllegalArgumentException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }
            String encodedNewPW = passwordEncoder.encode(dto.getNewPW());
            // 비밀번호 포함해서 updateInfo 호출
            member.updateInfo(encodedNewPW, dto.getUserEmail(), dto.getTel());
            log.info("비밀번호가 변경되었습니다.");
        } else {
            // 비밀번호 변경 없으면 기존 비밀번호 유지하며 이메일, 전화번호만 업데이트
            member.updateInfo(member.getUserPW(), dto.getUserEmail(), dto.getTel());
        }

        // 닉네임, 이름 변경이 필요하면 아래 주석 해제 후 엔티티에 메서드 추가 및 호출하세요
        /*
        if(dto.getNickName() != null && !dto.getNickName().isBlank()) {
            member.changeNickName(dto.getNickName());
        }

        if(dto.getUserName() != null && !dto.getUserName().isBlank()) {
            member.changeUserName(dto.getUserName());
        }
        */

        mypageModifyDao.save(member);
    }
}
