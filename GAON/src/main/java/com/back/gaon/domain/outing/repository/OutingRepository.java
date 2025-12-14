package com.back.gaon.domain.outing.repository;

import com.back.gaon.domain.outing.entity.Outing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutingRepository extends JpaRepository<Outing, Long> {
    List<Outing> findByScheduleId(Long scheduleId);
}
