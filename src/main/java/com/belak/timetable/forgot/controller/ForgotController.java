package com.belak.timetable.forgot.controller;

import com.belak.timetable.passwordresettoken.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forgot")
public class ForgotController {
    private final  PasswordResetService passwordResetService ;
    public ForgotController (PasswordResetService passwordResetService)
    {
        this.passwordResetService=passwordResetService ;
    }
    @GetMapping
   public String showForgot()
   {
       return "login/forgot";
   }
    @PostMapping
    public String processForgotPassword(@RequestParam("email") String email) {


        passwordResetService.createPasswordResetToken(email);

        return "redirect:/login?resetEmailSent";
    }

}
