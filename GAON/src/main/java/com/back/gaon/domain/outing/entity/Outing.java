package com.back.gaon.domain.outing.entity;

import com.back.gaon.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "outing")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Outing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 스케줄의 외출인가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalTime startTime;   // 외출 시작
    private LocalTime endTime;     // 외출 끝

    private String title;          // "국어학원", "치과" 등
}
