package com.belak.timetable.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/";

        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "ROLE_ADMIN":
                    redirectUrl = "/admin/dashboard";
                    break;
                case "ROLE_PROFESSOR":
                    redirectUrl = "/professor/dashboard";
                    break;
                case "ROLE_STUDENT":
                    redirectUrl = "/student/dashboard";
                    break;

            }
        }

        // Redirection vers la bonne page
        response.sendRedirect(redirectUrl);
    }
}
