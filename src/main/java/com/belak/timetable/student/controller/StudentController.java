package com.belak.timetable.student.controller;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.grouptimetable.service.GroupTimetableService;
import com.belak.timetable.student.dto.StudentDto;
import com.belak.timetable.student.entity.StudentEntity;
import com.belak.timetable.student.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService ;
    private final GroupTimetableService groupTimetableService ;
    StudentController(StudentService studentService ,
     GroupTimetableService groupTimetableService)
    {
        this.studentService=studentService ;
        this.groupTimetableService=groupTimetableService ;
    }
    @GetMapping("/dashboard")
    public String showStudentDashboard(Authentication authentication, Model model,
                                       HttpSession session)
    {
        StudentDto studentDto = studentService.createStudentDto(authentication.getName());
        model.addAttribute("fullname",
                studentDto.getFirstName()+" "+studentDto.getLastName());
        return "student/student-dashboard";
    }
    @GetMapping("/profile")
    public String showStudentProfile(Authentication authentication,Model model
    ,HttpSession session)
    {
        return "student/student-profile";
    }
    @GetMapping("/timetable")
    public String showStudentTimetable(Authentication authentication, Model model,
                                         HttpSession session)
    {


        return "student/student-timetable.html";
    }
    // Endpoint REST pour envoyer le fichier Excel
    @GetMapping("/timetablepdf")
    public ResponseEntity<byte[]> openSchedule(Authentication authentication) {

        String userId = authentication.getName(); // username connecté

        byte[] pdfData = studentService.getTimetableFile(userId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=timetable.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }


    @GetMapping("/preview")
    public ResponseEntity<byte[]> getPreview(Authentication authentication) throws IOException {



        byte[] image = groupTimetableService.getStudentPreview(authentication.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }



}
