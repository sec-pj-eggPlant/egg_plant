package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.mypage.repository.MypageModifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageModifyDao {
    private final MypageModifyRepository mypageModifyRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageModifyRepository.findByUserID(userID);
    }

    public Optional<Member> findById(Integer id) {
        return mypageModifyRepository.findById(id);
    }

    public Member save(Member member) {
        return mypageModifyRepository.save(member);
    }
}
