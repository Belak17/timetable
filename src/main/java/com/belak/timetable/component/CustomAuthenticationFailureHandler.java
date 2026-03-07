package com.belak.timetable.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException exception
    ) throws IOException, ServletException {

        String errorMessage = "Identifiants incorrects";

        // Tu peux détecter le type d'exception
        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Utilisateur introuvable";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Mot de passe incorrect";
        }

        // Tu peux stocker le message dans la session
        request.getSession().setAttribute("failure", errorMessage);

        // Rediriger vers la page login
        response.sendRedirect("/login");
    }
}
