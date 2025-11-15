// src/main/java/com/back/gaon/domain/schedule/mapper/ScheduleTemplateVersionMapper.java
package com.back.gaon.domain.schedule.version.mapper;

import com.back.gaon.domain.schedule.version.dto.request.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionDetailResponse;
import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;

public final class ScheduleTemplateVersionMapper {
    private ScheduleTemplateVersionMapper() {
    }

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

    public static ScheduleTemplateVersionCreateResponse toCreateResponse(ScheduleTemplateVersion v) {
        return new ScheduleTemplateVersionCreateResponse(
                v.getId(),
                v.getTemplate().getId(),
                v.getVersionNo(),
                v.getStatus()
        );
    }

    public static ScheduleTemplateVersionDetailResponse toVersionDetailResponse(ScheduleTemplateVersion v) {
        return new ScheduleTemplateVersionDetailResponse(
                v.getId(),
                v.getTemplate().getId(),   // FK → templateId
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