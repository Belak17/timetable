package com.belak.timetable.admin.service;

import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.admin.entity.AdminEntity;
import com.belak.timetable.admin.repository.AdminRepository;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.grouptimetable.repository.GroupTimetableRepository;
import com.belak.timetable.login.dto.LoginDto;
import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.professortimetable.repository.ProfessorTimetableRepository;
import com.belak.timetable.professortimetable.service.ProfessorTimetableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .userId(adminEntity.getUserId())
                .firstName(adminEntity.getFirstname())
                .lastName(adminEntity.getLastname())
                .email(adminEntity.getEmail())
                .build();
    }



}

