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

    public Page<Member> findByRole(Role role, Pageable pageable) {
        return adminMemberRepository.findByRole(role, pageable);
    }

    public List<Member> findByRole(Role role) {
        return adminMemberRepository.findByRole(role);
    }

    public List<Member> getAllMembers() {
        return adminMemberRepository.findAll();
    }

    public Page<Member> searchMembers(String category, String keyword, Pageable pageable) {
        if ((category == null || keyword == null || keyword.isBlank())) {
            return adminMemberRepository.findAllCustomSorted(pageable);
        }

        return switch (category) {
            case "userName" -> adminMemberRepository.findByUserNameContainingIgnoreCase(keyword, pageable);
            case "userID" -> adminMemberRepository.findByUserIDContainingIgnoreCase(keyword, pageable);
            default -> adminMemberRepository.findAll(pageable);
        };
    }
}
