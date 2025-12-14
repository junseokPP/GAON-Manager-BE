package com.back.gaon.domain.schedule.dto;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import com.back.gaon.domain.outing.dto.OutingResponse;
import com.back.gaon.domain.schedule.entity.Schedule;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public record ScheduleWithAttendanceResponse(
        Long studentId,
        String studentName,

        // 스케줄 정보
        LocalTime scheduledAttendTime,
        LocalTime scheduledLeaveTime,
        List<OutingResponse> scheduledOutings,
        String memo,

        // 출결 정보
        String attendanceStatus,     // PRESENT / OUTING / LEAVE / ABSENT / NONE
        LocalTime actualAttendTime,
        LocalTime actualLeaveTime,
        boolean isOuting,
        boolean excuseLate,
        boolean excuseAbsent,

        // 프론트 표시용 텍스트 (출석/외출중/하원/무단결석 등)
        String attendanceStatusText
) {

    public static ScheduleWithAttendanceResponse from(Schedule schedule, Attendance attendance) {

        // 출결 상태 텍스트 매핑
        String statusText = "미등원";
        boolean outingNow = false;
        String statusCode = "NONE";

        if (attendance != null) {
            statusCode = attendance.getStatus().name();

            switch (attendance.getStatus()) {
                case PRESENT -> statusText = "출석";
                case OUTING -> {
                    statusText = "외출중";
                    outingNow = true;
                }
                case LEAVE -> statusText = "하원";
                case ABSENT -> statusText = "무단결석";
                default -> statusText = "미등원";
            }
        }

        return ScheduleWithAttendanceResponse.builder()
                .studentId(schedule.getStudent().getId())
                .studentName(schedule.getStudent().getName())

                .scheduledAttendTime(schedule.getAttendTime())
                .scheduledLeaveTime(schedule.getLeaveTime())
                .scheduledOutings(schedule.getOutings() == null ? null :
                        schedule.getOutings().stream().map(OutingResponse::from).toList())
                .memo(schedule.getMemo())

                .attendanceStatus(statusCode)
                .actualAttendTime(attendance != null ? attendance.getAttendTime() : null)
                .actualLeaveTime(attendance != null ? attendance.getLeaveTime() : null)
                .isOuting(outingNow)
                .excuseLate(attendance != null && attendance.isExcuseLate())
                .excuseAbsent(attendance != null && attendance.isExcuseAbsent())

                .attendanceStatusText(statusText)
                .build();
    }
}
