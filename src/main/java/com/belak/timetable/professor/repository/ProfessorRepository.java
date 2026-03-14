package com.belak.timetable.professor.repository;

import com.belak.timetable.professor.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface ProfessorRepository  extends JpaRepository<ProfessorEntity,Long> {



    Optional<ProfessorEntity> findByUsername(String username);

    @Query("""
SELECT p FROM ProfessorEntity p
WHERE LOWER(TRIM(p.prenom)) = LOWER(TRIM(:prenom))
AND LOWER(TRIM(p.nom)) = LOWER(TRIM(:nom))
""")
    Optional<ProfessorEntity> findByNameNormalized(
            @Param("prenom") String prenom,
            @Param("nom") String nom
    );

    @Query("""
SELECT p FROM ProfessorEntity p
LEFT JOIN FETCH p.timetable
WHERE p.userName = :userName
""")
    Optional<ProfessorEntity> findByUsernameWithTimetable(String username);
}
