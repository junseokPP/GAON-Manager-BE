package com.back.gaon.domain.schedule.schedule.entity;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.schedule.schedule.enums.ScheduleStatus;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.item.entity.ScheduleTemplateItem;
import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "schedule",
        indexes = {
                @Index(name = "idx_schedule_member_date", columnList = "member_id, schedule_date"),
                @Index(name = "idx_schedule_version", columnList = "template_version_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 학생 스케줄인지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /** 어떤 템플릿 버전으로부터 생성됐는지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_version_id", nullable = false)
    private ScheduleTemplateVersion templateVersion;

    /** 어떤 아이템에서 파생됐는지 (선택) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_item_id")
    private ScheduleTemplateItem templateItem;

    /** 실 날짜 */
    @Column(name = "schedule_date", nullable = false)
    private LocalDate date;

    /** 시간 범위 */
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /** 과목/설명 */
    @Column(name = "subject", length = 50)
    private String subject;

    @Column(name = "memo", length = 255)
    private String memo;

    /** 상태 (정상 / 변경 / 결석 / 지각 등) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScheduleStatus status = ScheduleStatus.NORMAL;
}