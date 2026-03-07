package com.belak.timetable.student.repository;

import com.belak.timetable.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {


    List<StudentEntity> findByDepartmentAndFieldAndYearAndGroup(String department,String field,int year,String group);

    Optional<StudentEntity> findByUserId(String userId);

}
