package com.back.gaon.domain.attendance.repository;

import com.back.gaon.domain.attendance.entity.OutingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutingLogRepository extends JpaRepository<OutingLog,Long> {
}
