package com.back.gaon.domain.attendance.enums;

public enum AttendanceEventType {
    CHECK_IN,     // 등원
    CHECK_OUT,    // 하원
    OUTING,       // 외출
    RETURN,       // 복귀

    MARK_REPORTED_LATE,   // 통보지각 처리
    MARK_REPORTED_ABSENT // 통보결석 처리
}
