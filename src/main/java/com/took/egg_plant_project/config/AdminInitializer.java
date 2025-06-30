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
        Optional<Member> adminMember = memberDao.findByUserID("admin");
        if (adminMember.isEmpty()) {
            Member admin = Member.builder()
                    .userID("admin")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .nickName("admin")
                    .userName("관리자")
                    .userEmail("admin@admin.com")
                    .tel("010-8765-4321")
                    .role(Role.ROLE_ADMIN)
                    .build();
            memberDao.save(admin);
        } else {
            System.out.println("관리자 계정이 이미 있습니다.");
        }
        Optional<Member> ownerMember = memberDao.findByUserID("owner");
        if (ownerMember.isEmpty()) {
            Member owner = Member.builder()
                    .userID("owner")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .nickName("iAmOwner")
                    .userName("ownerName")
                    .userEmail("owner@naver.com")
                    .tel("010-1234-5678")
                    .role(Role.ROLE_OWNER)
                    .build();
            memberDao.save(owner);
        }
        Optional<Member> renterMember = memberDao.findByUserID("renter");
        if (renterMember.isEmpty()) {
            Member renter = Member.builder()
                    .userID("renter")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .nickName("iAmRenter")
                    .userName("renterName")
                    .userEmail("renter@naver.com")
                    .tel("010-9876-5432")
                    .role(Role.ROLE_RENTER)
                    .build();
            memberDao.save(renter);
        }
    }
}
