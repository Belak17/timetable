package com.belak.timetable.professor.controller;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.professor.dto.ProfessorDto;
import com.belak.timetable.professor.service.ProfessorService;
import com.belak.timetable.professortimetable.service.ProfessorTimetableService;
import com.belak.timetable.student.service.StudentService;
import com.belak.timetable.user.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService ;
    private final ProfessorTimetableService professorTimetableService;
    private  final UserDetailsServiceImpl userDetailsService ;
    ProfessorController(ProfessorService professorService ,
    ProfessorTimetableService professorTimetableService , UserDetailsServiceImpl userDetailsService)
    {
        this.professorTimetableService=professorTimetableService ;
        this.professorService=professorService ;
        this.userDetailsService=userDetailsService ;
    }
    @GetMapping("/dashboard")
    public String showProfessorDashboard(Authentication authentication, Model model
    , HttpSession session)
    {
        ProfessorDto professorDto = professorService.createProfessorDto(authentication.getName());
        model.addAttribute("fullname",
                professorDto.getFirstName()+" "+professorDto.getLastName());
        return "professor/professor-app";
    }
    @GetMapping("/profile")
    public String showProfessorProfile(Authentication authentication , Model model
    ,HttpSession session)
    {

        return "professor/professor-profile";
    }
    @GetMapping("/timetable")
    public String showProfessorTimetable(Authentication authentication, Model model,
                                         HttpSession session)
    {


        return "professor/professor-timetable";
    }


    // Endpoint REST pour envoyer le fichier Excel
    @GetMapping("/timetablepdf")
    public ResponseEntity<byte[]> openSchedule(Authentication authentication) {

        String userId = authentication.getName(); // username connecté

        byte[] pdfData = professorService.getTimetableFile(userId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=timetable.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }


    @GetMapping("/preview")
    public ResponseEntity<byte[]> getPreview(Authentication authentication) throws IOException {

        byte[] image = professorTimetableService
                .getProfessorPreview(authentication.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}


