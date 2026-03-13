package com.belak.timetable.admin.service;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.admin.entity.AdminEntity;
import com.belak.timetable.admin.repository.AdminRepository;
import com.belak.timetable.grouptimetable.repository.GroupTimetableRepository;
import com.belak.timetable.professortimetable.repository.ProfessorTimetableRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private  final GroupTimetableRepository groupTimetableRepository ;
    private final ProfessorTimetableRepository professorTimetableRepository ;
    AdminService(AdminRepository adminRepository ,
                 GroupTimetableRepository groupTimetableRepository ,
                 ProfessorTimetableRepository professorTimetableRepository ) {
        this.adminRepository = adminRepository;
        this.groupTimetableRepository=groupTimetableRepository;
        this.professorTimetableRepository=professorTimetableRepository ;
    }

    public AdminDto createAdminDto(String userId)
    {
        AdminEntity adminEntity = adminRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé pour userId : " + userId));

        return AdminDto.builder()
                .userId(adminEntity.getUsername())
                .firstName(adminEntity.getPrenom())
                .lastName(adminEntity.getNom())
                .email(adminEntity.getEmail())
                .build();
    }



}

