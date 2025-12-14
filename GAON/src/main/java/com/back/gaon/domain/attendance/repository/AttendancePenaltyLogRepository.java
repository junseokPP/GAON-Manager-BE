package com.back.gaon.domain.attendance.repository;

import com.back.gaon.domain.attendance.entity.AttendancePenaltyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendancePenaltyLogRepository extends JpaRepository<AttendancePenaltyLog, Long> {
}
