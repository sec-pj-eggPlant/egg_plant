package com.took.egg_plant_project.communal;

import com.took.egg_plant_project.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member loggedMember;
    private Map<String, Object> oAuth2UserInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(loggedMember.getRole().name()));
    }

    @Override
    public String getPassword() {
        return loggedMember.getUserPW();
    }

    @Override
    public String getUsername() {
        return loggedMember.getUserID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Integer getId() {
        return loggedMember.getId();
    }
}
