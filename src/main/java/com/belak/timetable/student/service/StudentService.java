package com.belak.timetable.student.service;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.admin.entity.AdminEntity;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.login.dto.LoginDto;
import com.belak.timetable.professor.entity.ProfessorEntity;
import com.belak.timetable.student.dto.StudentDto;
import com.belak.timetable.student.entity.StudentEntity;
import com.belak.timetable.student.repository.StudentRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Service
@Transactional
public class StudentService {
    StudentRepository studentRepository ;
    StudentService(StudentRepository studentRepository)
    {
        this.studentRepository=studentRepository ;
    }
    public StudentEntity createStudent(StudentEntity studentEntity)
    {
        return studentRepository.save(studentEntity);
    }
    public StudentDto createStudentDto(String userId)
    {
        StudentEntity studentEntity = studentRepository.findByUserId(userId).get();

        return StudentDto.builder()
                .userId(studentEntity.getUserId())
                .firstName(studentEntity.getFirstname())
                .lastName(studentEntity.getLastname())
                .email(studentEntity.getEmail())
                .build();
    }

    public byte[] getTimetableFile(String userId) {
        StudentEntity student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));

        if (student.getGroupTimetable() == null) {
            throw new RuntimeException("Emploi du temps non disponible");
        }

        return student.getGroupTimetable().getFileData();
    }

}
