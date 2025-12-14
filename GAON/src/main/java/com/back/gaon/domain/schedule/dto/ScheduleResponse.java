package com.back.gaon.domain.schedule.dto;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.outing.dto.OutingResponse;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.schedule.entity.Schedule;

import java.time.LocalTime;
import java.util.List;

public record ScheduleResponse(
        Long studentId,
        String studentName,
        LocalTime scheduledAttendTime,
        LocalTime scheduledLeaveTime,
        List<OutingResponse> scheduledOutings,
        String finalStatus,    // 출석/하원/외출중/미등원/무단결석
        boolean excuseLate,
        boolean excuseAbsent
) {
    public static ScheduleResponse from(Schedule schedule,
                                        List<Outing> outings,
                                        Attendance attendance) {

        String status = "미등원";
        boolean late = false;
        boolean absent = false;

        if (attendance != null) {
            status = switch (attendance.getStatus()) {
                case PRESENT -> "출석";
                case LEAVE -> "하원";
                case OUTING -> "외출중";
                case ABSENT -> "무단결석";
                default -> "미등원";
            };

            late = attendance.isExcuseLate();
            absent = attendance.isExcuseAbsent();
        }

        return new ScheduleResponse(
                schedule.getStudent().getId(),
                schedule.getStudent().getName(),
                schedule.getAttendTime(),
                schedule.getLeaveTime(),
                outings.stream()
                        .map(OutingResponse::from)
                        .toList(),
                status,
                late,
                absent
        );
    }


}


