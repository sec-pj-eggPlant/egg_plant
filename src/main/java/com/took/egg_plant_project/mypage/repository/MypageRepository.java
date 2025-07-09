package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MypageRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);
}
