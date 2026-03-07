package com.belak.timetable.student.entity;

import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity  extends UserEntity {
    private String department ;
    @Column(name = "student_field")
    private String field ;
    @Column(name = "student_year")
    private int year ;

    @Column(name = "student_group")
    private String group ;
    @ManyToOne
    @JoinColumn(name = "grouptimetable_id")
    private GroupTimetableEntity groupTimetable;



}
