package com.back.gaon.domain.schedule.service.template;

import com.back.gaon.domain.schedule.dto.request.template.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.dto.response.template.ScheduleTemplateResponse;

public interface ScheduleTemplateService {
    ScheduleTemplateResponse create(ScheduleTemplateCreateRequest req /*, Authentication auth */);

}
