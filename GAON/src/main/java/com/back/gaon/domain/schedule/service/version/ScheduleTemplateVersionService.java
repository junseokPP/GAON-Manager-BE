package com.back.gaon.domain.schedule.service.version;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionResponse;

public interface ScheduleTemplateVersionService {
    ScheduleTemplateVersionResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */);
}