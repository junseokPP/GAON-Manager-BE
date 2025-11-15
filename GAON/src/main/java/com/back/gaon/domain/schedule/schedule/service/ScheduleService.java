package com.back.gaon.domain.schedule.schedule.service;

import com.back.gaon.domain.schedule.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    /**
     * ✅ 승인된 템플릿 버전으로부터 일정 생성
     *
     * @param templateVersionId 스케줄의 기준이 되는 템플릿 버전 ID (보통 APPROVED 상태)
     * @param from 시작 날짜 (포함)
     * @param to   종료 날짜 (포함)
     *
     * @return 생성된 Schedule 목록
     */
    List<Schedule> generateSchedulesFromVersion(Long templateVersionId,
                                                LocalDate from,
                                                LocalDate to);

    /**
     * ✅ 특정 학생의 스케줄을 날짜 범위로 조회
     */
    List<Schedule> getSchedules(Long memberId, LocalDate from, LocalDate to);
}