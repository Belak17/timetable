package com.belak.timetable.administrator.repository;

import com.belak.timetable.administrator.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
}
