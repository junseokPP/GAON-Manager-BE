package com.back.gaon.domain.schedule.version.entity;

import com.back.gaon.domain.schedule.item.entity.ScheduleTemplateItem;
import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 템플릿의 한 차수(버전). 제출/승인/반려 단위
 * - status: DRAFT/PENDING/APPROVED/REJECTED
 * - effectiveFrom: 승인본의 적용 시작일(보통 다음 주 월요일)
 * - rejectReason: 반려 사유
 * - createdBy/reviewedBy: 작성자/검토자(감사)
 * - items: 요일·시간 블록(1:N)
 */
@Entity
@Table(name = "schedule_template_version",
        indexes = {
                @Index(name = "idx_stv_template", columnList = "template_id"),
                @Index(name = "idx_stv_status_eff", columnList = "status, effective_from")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScheduleTemplateVersion extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private ScheduleTemplate template;

    @Column(name = "version_no")
    private Integer versionNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TemplateStatus status;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "reject_reason", length = 255)
    private String rejectReason;

    @Column(name = "created_by")
    private Long createdBy;   // 학생 memberId 또는 관리자 adminId

    @Column(name = "reviewed_by")
    private Long reviewedBy;  // 승인/반려 처리한 관리자 ID

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayOfWeek ASC, startTime ASC")
    private List<ScheduleTemplateItem> items = new ArrayList<>();
}
