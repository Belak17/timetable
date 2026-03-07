package com.belak.timetable.admin.entity;

import com.belak.timetable.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter

public class AdminEntity extends UserEntity {
}
