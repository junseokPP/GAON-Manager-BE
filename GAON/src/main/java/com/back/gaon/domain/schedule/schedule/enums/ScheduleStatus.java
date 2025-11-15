package com.back.gaon.domain.schedule.schedule.enums;

public enum ScheduleStatus {
    NORMAL,     // 정상
    CHANGED,    // 하루만 수업/시간 변경됨
    CANCELED,   // 취소됨 (휴원, 학생 휴가 등)
    ABSENT,     // 결석
    LATE        // 지각
}