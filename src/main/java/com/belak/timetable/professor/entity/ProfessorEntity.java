package com.belak.timetable.professor.entity;

import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "professor")
public class ProfessorEntity extends UserEntity {
    private String speciality ;
    private String grade ;

    private String schoolStatus ;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_id") // Clé étrangère dans professor
    private ProfessorTimetableEntity timetable;

}
