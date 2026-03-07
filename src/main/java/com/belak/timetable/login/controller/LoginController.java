package com.belak.timetable.login.controller;


import com.belak.timetable.login.dto.LoginDto;
import com.belak.timetable.login.dto.LoginResponse;
import com.belak.timetable.login.service.LoginService;
import com.belak.timetable.student.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService ;
    LoginController(LoginService loginService)
    {
        this.loginService=loginService ;
    }
    @GetMapping
    public String showLogin()
    {
        return "login/login" ;
    }


}
