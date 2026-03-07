package com.belak.timetable.main.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectAfterLogin(Authentication authentication) {

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_PROFESSOR"))) {
            return "redirect:/professor/dashboard";
        }

        return "redirect:/student/dashboard";
    }
}
