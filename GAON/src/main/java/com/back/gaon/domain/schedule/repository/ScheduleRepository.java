package com.back.gaon.domain.schedule.repository;

import com.back.gaon.domain.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByStudentIdAndDay(Long studentId, DayOfWeek day);
    List<Schedule> findByStudentId(Long studentId);
    List<Schedule> findByDay(DayOfWeek day);
    List<Schedule> findByDayAndStatus(DayOfWeek day, ScheduleStatus status);

    @Query("""
    select distinct s
    from Schedule s
    left join fetch s.outings o
    where s.day = :day and s.status = :status
""")
    List<Schedule> findByDayWithOutings(@Param("day") DayOfWeek day, @Param("status") ScheduleStatus status);

}
