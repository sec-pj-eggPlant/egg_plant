package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByRole(Role role);

    List<Member> findByUserNameContainingIgnoreCase(String keyword);

    List<Member> findByUserIDContainingIgnoreCase(String keyword);

    long countByRole(Role role);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.role NOT IN (:roles)")
    long countByRoleNotIn(List<Role> roles);
}
