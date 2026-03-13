package com.belak.timetable.student.entity;

import com.belak.timetable.enumeration.Departement;
import com.belak.timetable.enumeration.Filiere;
import com.belak.timetable.enumeration.Nationalite;
import com.belak.timetable.enumeration.TypeDiplome;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity  extends UserEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "student_field")
    private Filiere filiere;
    @Column(name = "student_year")

    private Integer niveau;

    @Column(name = "student_group")
    private String group ;
    @ManyToOne
    @JoinColumn(name = "grouptimetable_id")
    private GroupTimetableEntity groupTimetable;



    private String codeDiplome;
    private String nomDiplome;

    @Enumerated(EnumType.STRING)
    private TypeDiplome typeDiplome;

    private String numeroInscription;

    //private String groupeC;
    //private String groupeTD;
    //private String groupeM;
    //private String groupeArchive;


}

