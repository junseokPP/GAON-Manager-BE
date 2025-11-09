package com.back.gaon.domain.schedule.entity;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

/**
 * "학생 1명당 1개" 권장되는 주간 스케줄의 논리 템플릿
 * - memberId: 소유 학생 식별자(Long)
 * - currentApprovedVersionId: 현재 운영 반영 중인 승인 버전 ID(옵셔널)
 * - versions: 이 템플릿의 모든 버전 목록(1:N)
 */

@Entity
@Table(
        name = "schedule_template",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_template_member_type",
                columnNames = {"member_id"}
        ),
        indexes = @Index(
                name = "idx_member_type",
                columnList = "member_id"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleTemplate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull//자바객체에서 null이면 안된다 dto->entity 저장시
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//JPA가 SQL 만들 때 이 관계는 필수라고 판단
    @JoinColumn(name = "member_id", nullable = false)//db단에서 null허용안한다.
    private Member member;

    @Column(name = "current_approved_version_id")
    private Long currentApprovedVersionId;

    ///mappedBy = "template" 외래키를가진 template_id가 주체다. 이클래스는 읽기전용
    ///cascade = ALL 부모 삭제,저장 등등 시 자식도 삭제,저장 등등
    ///고아발생시 삭제
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleTemplateVersion> versions =  new ArrayList<>();
}