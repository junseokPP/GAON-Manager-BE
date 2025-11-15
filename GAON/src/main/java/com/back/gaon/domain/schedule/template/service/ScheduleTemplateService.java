package com.back.gaon.domain.schedule.template.service;

import com.back.gaon.domain.schedule.template.dto.request.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateCreateResponse;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateDetailResponse;

import java.util.List;

public interface ScheduleTemplateService {
    ScheduleTemplateCreateResponse create(ScheduleTemplateCreateRequest req /*, Authentication auth */);
    ScheduleTemplateDetailResponse findById(Long id);
    List<ScheduleTemplateDetailResponse> findAll();
}
