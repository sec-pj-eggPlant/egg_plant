package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Member, Integer> {

    // ✔ 페이징 없는 role 검색 (e.g., trade 페이지 등에서 사용)
    List<Member> findByRole(Role role);

    // ✔ 페이징 지원 role 검색
    Page<Member> findByRole(Role role, Pageable pageable);

    // ✔ 이름 기준 검색 (페이징)
    Page<Member> findByUserNameContainingIgnoreCase(String keyword, Pageable pageable);

    // ✔ 아이디 기준 검색 (페이징)
    Page<Member> findByUserIDContainingIgnoreCase(String keyword, Pageable pageable);

    // ✔ 전체 조회 (페이징용)
    Page<Member> findAll(Pageable pageable);

    // ✔ role별 카운트
    long countByRole(Role role);

    // ✔ ROLE_RENTER, ROLE_OWNER가 아닌 사용자 수 조회
    @Query("SELECT COUNT(m) FROM Member m WHERE m.role NOT IN (:roles)")
    long countByRoleNotIn(List<Role> roles);
}
