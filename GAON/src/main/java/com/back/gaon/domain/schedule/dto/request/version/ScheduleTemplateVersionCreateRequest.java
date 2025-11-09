// src/main/java/com/back/gaon/domain/schedule/dto/request/version/ScheduleTemplateVersionCreateRequest.java
package com.back.gaon.domain.schedule.dto.request.version;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ScheduleTemplateVersionCreateRequest(
        @NotNull Long templateId,
        Integer versionNo,              // null이면 자동 채번(가장 큰 번호 + 1)
        LocalDate effectiveFrom,        // 선택: 운영 반영 기준일(승인 시 사용 가능)
        Boolean submit                  // null/false -> DRAFT, true -> PENDING
) {}