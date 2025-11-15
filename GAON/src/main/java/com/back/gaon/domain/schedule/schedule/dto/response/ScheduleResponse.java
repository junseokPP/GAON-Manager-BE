package com.back.gaon.domain.schedule.schedule.dto.response;

import com.back.gaon.domain.schedule.schedule.enums.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleResponse(
        Long id,
        Long memberId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String subject,
        String memo,
        ScheduleStatus status
) {}