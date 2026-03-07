package com.belak.timetable.login.service;

import com.belak.timetable.admin.service.AdminService;
import com.belak.timetable.login.dto.LoginDto;
import com.belak.timetable.login.dto.LoginResponse;
import com.belak.timetable.professor.service.ProfessorService;
import com.belak.timetable.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class LoginService {
    private final StudentService studentService ;
    private final ProfessorService professorService;
    private final AdminService adminService;
    LoginService(StudentService studentService,
                 ProfessorService professorService,
                 AdminService adminService)
    {
        this.adminService=adminService;
        this.professorService=professorService;
        this.studentService=studentService;
    }


}
