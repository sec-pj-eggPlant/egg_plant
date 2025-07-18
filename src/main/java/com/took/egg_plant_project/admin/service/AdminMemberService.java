package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminMemberRepository;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    // ✅ 페이징 가능한 ROLE 검색 (e.g., /admin/member)
    public Page<Member> findByRole(Role role, Pageable pageable) {
        return adminMemberRepository.findByRole(role, pageable);
    }

    // ✅ 전체 ROLE 검색 (e.g., /admin/trade에서 목록 조회 용도)
    public List<Member> findByRole(Role role) {
        return adminMemberRepository.findByRole(role);
    }

    // ✅ 전체 멤버 조회 (페이징 없음)
    public List<Member> getAllMembers() {
        return adminMemberRepository.findAll();
    }

    // ✅ 검색 + 페이징 지원
    public Page<Member> searchMembers(String category, String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return adminMemberRepository.findAll(pageable);
        }

        return switch (category) {
            case "userName" -> adminMemberRepository.findByUserNameContainingIgnoreCase(keyword, pageable);
            case "userID" -> adminMemberRepository.findByUserIDContainingIgnoreCase(keyword, pageable);
            default -> adminMemberRepository.findAll(pageable);
        };
    }
}
