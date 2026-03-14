package com.belak.timetable.professor.service;

import com.belak.timetable.professor.dto.ProfessorDto;
import com.belak.timetable.professor.entity.ProfessorEntity;
import com.belak.timetable.professor.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProfessorService {
    private final ProfessorRepository professorRepository ;
    ProfessorService(ProfessorRepository professorRepository)
    {
        this.professorRepository=professorRepository ;
    }
    public ProfessorDto createProfessorDto(String userId) {

        ProfessorEntity professorEntity = professorRepository
                .findByUsername(userId)
                .orElseThrow(() ->
                        new RuntimeException("Professeur introuvable avec userId: " + userId));

        return ProfessorDto.builder()
                .userId(professorEntity.getUsername())
                .firstName(professorEntity.getPrenom())
                .lastName(professorEntity.getNom())
                .email(professorEntity.getEmail())
                .build();
    }

    public byte[] getTimetableFile(String userId) {

        ProfessorEntity professor = professorRepository.findByUsername(userId)
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));

        if (professor.getTimetable() == null) {
            throw new RuntimeException("Emploi du temps non disponible");
        }

        return professor.getTimetable().getFileData();
    }
    @Transactional(readOnly = true)
    public ProfessorEntity getProfessorEntity(Long id) {

        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    }
}
