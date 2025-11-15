package com.back.gaon.domain.schedule.template.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * submit=true 면 학생 생성시 PENDING, false면 DRAFT로 저장
 * 관리자는 submit 값과 무관하게 APPROVED
 */
public record ScheduleTemplateCreateRequest(
        @NotNull Long memberId,
        @Size(max = 100) String name,
        @Size(max = 255) String description,
        Boolean submit // null이면 기본 false로 처리
) {}
