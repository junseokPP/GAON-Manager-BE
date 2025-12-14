package com.back.gaon.domain.attendance.entity;

import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.entity.StudentDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendance")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 학생의 출결인가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Member student;

    // 날짜
    private LocalDate date;

    // 요일 (optional)
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")  // ← FIX: 예약어 회피
    private DayOfWeek day;


    // 실제 등원/하원 시간
    private LocalTime attendTime;
    private LocalTime leaveTime;

    // 외출 로그들
    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OutingLog> outingLogs = new ArrayList<>();

    // 현재 상태 (출석/하원/외출중/…)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    // 통보지각 / 통보결석을 눌렀는지 여부
    private boolean excuseLate;     // 통보지각
    private boolean excuseAbsent;   // 통보결석

    private String memo; // 관리자 메모
}
