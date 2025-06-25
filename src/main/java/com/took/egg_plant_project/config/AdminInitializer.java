package com.took.egg_plant_project.config;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    //application시작할때 동작한다.
    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<Member> optionalMember = memberDao.findByUserID("admin");
        if (!optionalMember.isPresent()) {
            Member adminMember = Member.builder()
                    .userID("admin")
                    .role(Role.ROLE_ADMIN)
                    .userName("관리자")
                    .userEmail("admin@admin.com")
                    .nickName("adminNickName")
                    .tel("01087654321")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .build();
            memberDao.save(adminMember);
        } else {
            System.out.println("관리자 계정이 이미 있습니다.");
        }
        Optional<Member> hongMember = memberDao.findByUserID("user");
        if (!hongMember.isPresent()) {
            Member member1 = Member.builder()
                    .userID("user")
                    .userName("userName")
                    .userEmail("user@naver.com")
                    .nickName("userNickName")
                    .tel("01012345678")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .role(Role.ROLE_OWNER)
                    .build();
            memberDao.save(member1);
        }

    }
}
