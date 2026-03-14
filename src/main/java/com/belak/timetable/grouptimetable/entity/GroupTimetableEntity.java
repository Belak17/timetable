package com.belak.timetable.grouptimetable.entity;

import com.belak.timetable.enumeration.Departement;
import com.belak.timetable.enumeration.Filiere;
import com.belak.timetable.student.entity.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "group_timetable")
public class GroupTimetableEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_seq"
    )
    @SequenceGenerator(
            name = "group_seq",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    private Long id ;
    private Departement departement;
    private Filiere filiere;
    private Integer niveau;
    @Column(name = "group_name")
    private String group;

    @Column(name = "position_index")
    private int position;
    private String filename ;
    private String contentType ;
    @Column(name = "data")
    private byte[] fileData;
    @OneToMany(mappedBy = "groupTimetable" , fetch = FetchType.LAZY)
    private List<StudentEntity> students = new ArrayList<>();
    public void addStudent(StudentEntity student){
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
        student.setGroupTimetable(this);
    }
}
