package com.back.gaon.domain.schedule.version.dto.response;

import com.back.gaon.domain.schedule.template.enums.TemplateStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleTemplateVersionDetailResponse(
        Long id,
        Long templateId,
        Integer versionNo,
        TemplateStatus status,
        LocalDate effectiveFrom,
        Long createdBy,
        Long reviewedBy,
        String rejectReason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}