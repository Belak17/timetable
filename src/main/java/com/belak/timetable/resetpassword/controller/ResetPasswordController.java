package com.belak.timetable.resetpassword.controller;

import com.belak.timetable.passwordresettoken.entity.PasswordResetToken;
import com.belak.timetable.passwordresettoken.repository.PasswordResetTokenRepository;
import com.belak.timetable.user.entity.UserEntity;
import com.belak.timetable.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ResetPasswordController {
    private final PasswordResetTokenRepository tokenRepository ;
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    public ResetPasswordController (PasswordResetTokenRepository tokenRepository,
                                    UserRepository userRepository , PasswordEncoder passwordEncoder)
    {
        this.tokenRepository=tokenRepository ;
        this.passwordEncoder=passwordEncoder ;
        this.userRepository = userRepository ;
    }
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }

        UserEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return "redirect:/login?passwordResetSuccess";
    }
}
