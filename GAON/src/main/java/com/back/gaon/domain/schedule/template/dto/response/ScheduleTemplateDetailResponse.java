package com.back.gaon.domain.schedule.template.dto.response;

import com.back.gaon.domain.schedule.template.enums.TemplateStatus;

import java.time.LocalDateTime;

public record ScheduleTemplateDetailResponse(
        Long id,
        Long memberId,
        String name,
        String description,
        TemplateStatus status,
        Long approvedBy,
        LocalDateTime approvedAt,
        Long currentApprovedVersionId
) {}