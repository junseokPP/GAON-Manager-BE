package com.back.gaon.domain.schedule.schedule.repository;

import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 특정 학생의 일정들을 날짜 범위로 조회
     * - 예: 한 달치, 일주일치 등
     */
    List<Schedule> findByMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(
            Long memberId,
            LocalDate from,
            LocalDate to
    );

    void deleteByMemberIdAndDateBetween(Long memberId, LocalDate from, LocalDate to);
}