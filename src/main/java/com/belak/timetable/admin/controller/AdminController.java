package com.belak.timetable.admin.controller;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.admin.service.AdminService;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.user.dto.UserDto;
import com.belak.timetable.user.service.UserDetailsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.belak.timetable.professortimetable.service.ProfessorTimetableService;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserDetailsServiceImpl userDetailsService ;
    AdminController(AdminService adminService , UserDetailsServiceImpl userDetailsService) {
        this.adminService = adminService;
        this.userDetailsService=userDetailsService ;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Authentication authentication , Model model ) {
        UserDto userDto = userDetailsService.createUserDto(authentication.getName());

        if(userDto != null){
            model.addAttribute("fullname",
                    userDto.getFirstName()+" " + userDto.getLastName());
        }else{
            model.addAttribute("fullname", "Admin");
        }
        return "admin/admin-dashboard.html";
    }


    @GetMapping("/profile")
    public String showAdminProfile(Authentication authentication, Model model) {
        return "admin/admin-profile.html";
    }


    @GetMapping("/timetable")
    public String showAdminUploadTimetable(Authentication authentication , Model model) {
        return "admin/admin-upload-timetable.html";
    }


}
