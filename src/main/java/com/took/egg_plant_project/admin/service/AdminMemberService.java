package com.took.egg_plant_project.admin.service;


import com.took.egg_plant_project.admin.repository.AdminMemberRepository;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    public List<Member> findByRole(Role role) {
        return adminMemberRepository.findByRole(role);
    }

    public List<Member> getAllMembers() {
        return adminMemberRepository.findAll();
    }

    public List<Member> searchMembers(String category, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return adminMemberRepository.findAll();
        }

        if ("userName".equals(category)) {
            return adminMemberRepository.findByUserNameContainingIgnoreCase(keyword);
        } else if ("userID".equals(category)) {
            return adminMemberRepository.findByUserIDContainingIgnoreCase(keyword);
        } else {
            return adminMemberRepository.findAll();
        }
    }

}