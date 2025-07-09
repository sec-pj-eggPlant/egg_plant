package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminMemberRepository;
import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.member.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final AdminMemberRepository adminMemberRepository;

    public Map<String, Long> getRoleStatistics() {
        long owner = adminMemberRepository.countByRole(Role.ROLE_OWNER);
        long renter = adminMemberRepository.countByRole(Role.ROLE_RENTER);
        long other = adminMemberRepository.countByRoleNotIn(List.of(Role.ROLE_OWNER, Role.ROLE_RENTER));

        long total = owner + renter + other;

        Map<String, Long> result = new HashMap<>();
        result.put("owner", owner);
        result.put("renter", renter);
        result.put("other", other);
        result.put("total", total);

        return result;
    }

    public long getTotalMemberCount() {
        return adminMemberRepository.count(); // 모든 회원 수
    }
}
