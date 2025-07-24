package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findByRole(Role role);

    Page<Member> findByRole(Role role, Pageable pageable);

    Page<Member> findByUserNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Member> findByUserIDContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Member> findAll(Pageable pageable);

    long countByRole(Role role);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.role NOT IN (:roles)")
    long countByRoleNotIn(List<Role> roles);

    @Query("""
    SELECT m FROM Member m
    ORDER BY m.createdAt DESC
""")
    Page<Member> findAllCustomSorted(Pageable pageable);
}
