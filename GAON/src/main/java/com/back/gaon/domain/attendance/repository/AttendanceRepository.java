package com.back.gaon.domain.attendance.repository;

import com.back.gaon.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate today);

    List<Attendance> findAllByDate(LocalDate today);

}
