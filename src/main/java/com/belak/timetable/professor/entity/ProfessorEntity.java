package com.belak.timetable.professor.entity;

import com.belak.timetable.enumeration.Grade;
import com.belak.timetable.enumeration.Statuts;
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
    private String specialite;
    private Grade grade ;
    private String codeGrade ;
    private String libelleGrade ;

    @Enumerated(EnumType.STRING)
    private Statuts schoolStatus ;
    private String codeStatus ;
    private String libelleStatus ;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_id") // Clé étrangère dans professor
    private ProfessorTimetableEntity timetable;

    private String etablissement_origine ;
    private String rib ;
    private String application_tiers ;


}
