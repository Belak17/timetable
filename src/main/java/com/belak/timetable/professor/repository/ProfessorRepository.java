package com.belak.timetable.professor.repository;

import com.belak.timetable.admin.entity.AdminEntity;
import com.belak.timetable.professor.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface ProfessorRepository  extends JpaRepository<ProfessorEntity,Long> {

    Optional<ProfessorEntity> findByFirstnameAndLastnameAndSpecialityAndGrade(String fisrtname, String lastname, String speciality, String grade);

    Optional<ProfessorEntity> findByUserId(String userId);

    @Query("""
SELECT p FROM ProfessorEntity p
WHERE LOWER(TRIM(p.firstname)) = LOWER(TRIM(:firstname))
AND LOWER(TRIM(p.lastname)) = LOWER(TRIM(:lastname))
""")
    Optional<ProfessorEntity> findByNameNormalized(
            @Param("firstname") String firstname,
            @Param("lastname") String lastname
    );

    @Query("""
SELECT p FROM ProfessorEntity p
LEFT JOIN FETCH p.timetable
WHERE p.userId = :userId
""")
    Optional<ProfessorEntity> findByUserIdWithTimetable(String userId);
}
