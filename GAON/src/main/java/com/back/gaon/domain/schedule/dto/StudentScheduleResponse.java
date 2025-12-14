package com.back.gaon.domain.schedule.dto;

import com.back.gaon.domain.outing.dto.OutingResponse;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.schedule.entity.Schedule;

import java.time.LocalTime;
import java.util.List;

public record StudentScheduleResponse(
        Long scheduleId,
        Long studentId,
        String studentName,
        String day,
        LocalTime attendTime,
        LocalTime leaveTime,
        String memo,
        List<OutingResponse> outings
) {
    public static StudentScheduleResponse from(Schedule schedule, List<Outing> outings) {
        return new StudentScheduleResponse(
                schedule.getId(),
                schedule.getStudent().getId(),
                schedule.getStudent().getName(),
                schedule.getDay().name(),
                schedule.getAttendTime(),
                schedule.getLeaveTime(),
                schedule.getMemo(),
                outings.stream().map(OutingResponse::from).toList()
        );
    }
}
