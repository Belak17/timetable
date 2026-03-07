package com.belak.timetable.professortimetable.entity;

import com.belak.timetable.professor.entity.ProfessorEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "professor_timetable")
public class ProfessorTimetableEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "timetable_seq"
    )
    @SequenceGenerator(
            name = "timetable_seq",
            sequenceName = "timetable_sequence",
            allocationSize = 1
    )
    private Long id ;
    private String speciality ;
    private String statut ;
    private String grade ;
    @Column(name = "position_index")
    private int position;
    private String filename ;

    @Column( name = "file_data")
    private byte[] fileData;
    private String contentType;
    // côté inverse
    @OneToOne(mappedBy = "timetable")
    private ProfessorEntity professor;






}
