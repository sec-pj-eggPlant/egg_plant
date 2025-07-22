package com.took.egg_plant_project.config;

import com.took.egg_plant_project.communal.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/home");
        } else {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String nickname = userDetails.getLoggedMember().getNickName();

            request.getSession().setAttribute("loginSuccessMessage", nickname + "님 환영합니다!");

            response.sendRedirect("/main/list");
        }
    }
}
