package com.back.gaon.domain.schedule.entity;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.schedule.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 학생의 스케줄인가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Member student;   // role = STUDENT

    // 요일 (MONDAY, TUESDAY, ...)
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week",nullable = false)
    private DayOfWeek day;

    @OneToMany(mappedBy = "schedule")
    @Column(name = "outings")
    private List<Outing> outings;

    // 등원 시간
    private LocalTime attendTime;

    // 하원 시간
    private LocalTime leaveTime;

    // 상태: APPROVED / PENDING / REJECTED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    private String memo;
}
