package com.back.gaon.domain.schedule.version.dto.response;

import com.back.gaon.domain.schedule.template.enums.TemplateStatus;

public record ScheduleTemplateVersionCreateResponse(
        Long id,
        Long templateId,
        Integer versionNo,
        TemplateStatus status
) {}