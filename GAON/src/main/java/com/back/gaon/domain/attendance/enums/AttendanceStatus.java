package com.back.gaon.domain.attendance.enums;

public enum AttendanceStatus {
    PRESENT ,       // 정상 출석
    LEAVE,          // 하원
    OUTING,         // 외출중
    ABSENT,         // 최종 결석
    NONE           // 아직 출결 시작 안됨
}
