package com.belak.timetable.grouptimetable.repository;

import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTimetableRepository  extends JpaRepository<GroupTimetableEntity,Long> {
}
