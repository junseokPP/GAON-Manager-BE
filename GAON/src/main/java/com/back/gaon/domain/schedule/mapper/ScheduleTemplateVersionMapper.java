// src/main/java/com/back/gaon/domain/schedule/mapper/ScheduleTemplateVersionMapper.java
package com.back.gaon.domain.schedule.mapper;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionResponse;
import com.back.gaon.domain.schedule.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.enums.TemplateStatus;

public final class ScheduleTemplateVersionMapper {
    private ScheduleTemplateVersionMapper() {}

    public static ScheduleTemplateVersion toEntity(ScheduleTemplateVersionCreateRequest req,
                                                   ScheduleTemplate template,
                                                   int versionNoResolved,
                                                   TemplateStatus status) {
        return ScheduleTemplateVersion.builder()
                .template(template)
                .versionNo(versionNoResolved)
                .status(status)
                .effectiveFrom(req.effectiveFrom())
                .createdBy(null)    // 시큐리티 붙이면 작성자 ID 세팅
                .reviewedBy(null)
                .rejectReason(null)
                .build();
    }

    public static ScheduleTemplateVersionResponse toResponse(ScheduleTemplateVersion v) {
        return new ScheduleTemplateVersionResponse(
                v.getId(),
                v.getTemplate().getId(),
                v.getVersionNo(),
                v.getStatus(),
                v.getEffectiveFrom(),
                v.getCreatedBy(),
                v.getReviewedBy(),
                v.getRejectReason(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}