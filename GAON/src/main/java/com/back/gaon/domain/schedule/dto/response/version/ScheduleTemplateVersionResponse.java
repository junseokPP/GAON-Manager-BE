package com.back.gaon.domain.schedule.dto.response.version;

import com.back.gaon.domain.schedule.enums.TemplateStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleTemplateVersionResponse(
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