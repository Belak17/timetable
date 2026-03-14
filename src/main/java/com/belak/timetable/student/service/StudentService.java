package com.belak.timetable.student.service;

import com.belak.timetable.student.dto.StudentDto;
import com.belak.timetable.student.entity.StudentEntity;
import com.belak.timetable.student.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        StudentEntity studentEntity = studentRepository.findByUsername(userId).get();

        return StudentDto.builder()
                .userId(studentEntity.getUsername())
                .firstName(studentEntity.getPrenom())
                .lastName(studentEntity.getNom())
                .email(studentEntity.getEmail())
                .build();
    }

    public byte[] getTimetableFile(String userId) {
        StudentEntity student = studentRepository.findByUsername(userId)
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));

        if (student.getGroupTimetable() == null) {
            throw new RuntimeException("Emploi du temps non disponible");
        }

        return student.getGroupTimetable().getFileData();
    }
    public Page<StudentEntity> getStudentByFieldAndYearAndGroup(String field,int year,String group,int page,int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByFiliereAndNiveauAndGroupe(field, ,year,group,pageable);
    }

}
