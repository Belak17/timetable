package com.belak.timetable.student.repository;

import com.belak.timetable.enumeration.Departement;
import com.belak.timetable.enumeration.Filiere;
import com.belak.timetable.student.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {


    List<StudentEntity> findByDepartementAndFiliereAndNiveauAndGroupe(
            Departement departement, // type enum
            Filiere filiere,         // type enum
            int niveau,
            String groupe
    );

    Optional<StudentEntity> findByUsername(String username);

    Page<StudentEntity> findByFiliereAndNiveauAndGroupe(Filiere filiere, Integer niveau, String group, Pageable pageable);
}
