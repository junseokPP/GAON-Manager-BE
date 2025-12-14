package com.back.gaon.domain.attendance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "attendance_outing")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    private LocalTime startTime;
    private LocalTime endTime;
}
