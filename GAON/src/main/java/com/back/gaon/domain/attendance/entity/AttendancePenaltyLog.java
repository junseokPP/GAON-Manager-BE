package com.back.gaon.domain.attendance.entity;

import com.back.gaon.domain.attendance.enums.PenaltyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_penalty")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendancePenaltyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    @Enumerated(EnumType.STRING)
    private PenaltyType type;  // LATE_ABSENT or ABSENT

    private LocalDateTime recordedAt;
}
