package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.mypage.repository.MypageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageDao {
    private final MypageRepository mypageRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageRepository.findByUserID(userID);
    }

    public Optional<Member> findById(Integer id) {
        return mypageRepository.findById(id);
    }

    public Member save(Member member) {
        return mypageRepository.save(member);
    }
}
