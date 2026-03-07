package com.belak.timetable.passwordresettoken.service;

import com.belak.timetable.passwordresettoken.entity.PasswordResetToken;
import com.belak.timetable.passwordresettoken.repository.PasswordResetTokenRepository;
import com.belak.timetable.user.entity.UserEntity;
import com.belak.timetable.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    public PasswordResetService(PasswordResetTokenRepository tokenRepository
    , UserRepository userRepository , JavaMailSender mailSender)
    {
        this.tokenRepository=tokenRepository ;
        this.userRepository=userRepository ;
        this.mailSender = mailSender ;
    }
    public void createPasswordResetToken(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(resetToken);

        sendEmail(user.getEmail(), token);
    }

    private void sendEmail(String to, String token) {

        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Réinitialisation mot de passe");
        message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe : \n" + resetUrl);

        mailSender.send(message);
    }
}
