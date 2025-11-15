package com.back.gaon.domain.schedule.template.dto.response;

public record ScheduleTemplateCreateResponse(
        Long id,
        Long memberId,
        String name,
        String description
) {}
