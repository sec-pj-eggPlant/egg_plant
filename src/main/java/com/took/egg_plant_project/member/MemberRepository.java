package com.took.egg_plant_project.member;

import com.took.egg_plant_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByUserID(String userID);
    boolean existsByUserID(String userID);
    boolean existsByUserEmail(String userEmail);
    boolean existsByNickName(String nickName);
}