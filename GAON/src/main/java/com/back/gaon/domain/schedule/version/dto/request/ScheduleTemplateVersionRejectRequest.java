package com.back.gaon.domain.schedule.version.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleTemplateVersionRejectRequest(
        @NotBlank(message = "반려 사유는 필수입니다.")
        String rejectReason
) {}