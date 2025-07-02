package com.egg_plant_project.member;

import com.egg_plant_project.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final MemberRepository memberRepository;

    public Optional<Member> findByUserID(String admin) {
        return memberRepository.findByUserID(admin);
    }

    public void save(Member savedMember) {
        memberRepository.save(savedMember);
    }
}
