package com.took.egg_plant_project.member;

import com.took.egg_plant_project.entity.Member;
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

    @Repository
    public static interface MemberRepository extends JpaRepository<Member, Integer> {

        Optional<Member> findByUserID(String admin);
    }
}
