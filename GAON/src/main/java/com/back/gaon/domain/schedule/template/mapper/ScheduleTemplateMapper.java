package com.back.gaon.domain.schedule.template.mapper;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.schedule.template.dto.request.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateCreateResponse;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateDetailResponse;
import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;

public class ScheduleTemplateMapper {

    public static ScheduleTemplate toEntity(ScheduleTemplateCreateRequest req, Member member) {
        return ScheduleTemplate.builder()
                .member(member)
                .name(req.name())
                .description(req.description())
                .status(TemplateStatus.DRAFT) // 기본값 (서비스에서 변경 가능)
                .build();
    }

    public static ScheduleTemplateCreateResponse toCreateResponse(ScheduleTemplate e) {
        return new ScheduleTemplateCreateResponse(
                e.getId(),
                e.getMember().getId(),
                e.getName(),
                e.getDescription()
        );
    }

    public static ScheduleTemplateDetailResponse toDetailResponse(ScheduleTemplate e) {
        return new ScheduleTemplateDetailResponse(
                e.getId(),
                e.getMember().getId(),
                e.getName(),
                e.getDescription(),
                e.getStatus(),
                e.getApprovedBy(),
                e.getApprovedAt(),
                e.getCurrentApprovedVersionId()
        );
    }
}