package com.back.gaon.domain.attendance.dto.response;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        Long attendanceId,
        Long studentId,
        LocalDate date,
        LocalTime attendTime,
        LocalTime leaveTime,
        boolean isOuting,
        boolean excuseLate,
        boolean excuseAbsent,
        String finalStatus
) {
    public static AttendanceResponse from(Attendance a) {

        // 화면 표시용 상태 텍스트
        String statusText = switch (a.getStatus()) {
            case OUTING -> "외출중";
            case LEAVE -> "하원";
            case PRESENT -> "출석";
            case ABSENT -> "무단결석";  // 자동/수동 결석 처리 시
            case NONE -> "미등원";
        };

        boolean outingNow = a.getStatus() == AttendanceStatus.OUTING;

        return new AttendanceResponse(
                a.getId(),
                a.getStudent().getId(),
                a.getDate(),
                a.getAttendTime(),
                a.getLeaveTime(),
                outingNow,
                a.isExcuseLate(),
                a.isExcuseAbsent(),
                statusText
        );
    }
}
