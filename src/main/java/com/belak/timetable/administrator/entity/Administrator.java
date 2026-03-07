package com.belak.timetable.administrator.entity;

import com.belak.timetable.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrator_entity")
public class Administrator extends UserEntity {
}
