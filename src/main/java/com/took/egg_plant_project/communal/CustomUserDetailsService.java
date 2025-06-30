package com.took.egg_plant_project.communal;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberDao.findByUserID(userID);

        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
        }
        Member member = optionalMember.get();
        return new CustomUserDetails(member);
    }
}
