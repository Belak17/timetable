package com.belak.timetable.admin.repository;

import com.belak.timetable.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {

    public Optional<AdminEntity> findByUserId(String userId);
}
