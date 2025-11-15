package com.back.gaon.domain.schedule.schedule.mapper;

import com.back.gaon.domain.schedule.schedule.dto.response.ScheduleResponse;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;

public class ScheduleMapper {

    public static ScheduleResponse toResponse(Schedule s) {
        return new ScheduleResponse(
                s.getId(),
                s.getMember().getId(),
                s.getDate(),
                s.getStartTime(),
                s.getEndTime(),
                s.getSubject(),
                s.getMemo(),
                s.getStatus()
        );
    }
}