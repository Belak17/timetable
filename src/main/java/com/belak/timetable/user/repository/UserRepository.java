package com.belak.timetable.user.repository;


import com.belak.timetable.user.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByEmail(String email);

    void deleteByUserId(String userId);
}
