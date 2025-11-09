package com.back.gaon.domain.schedule.repository;

import com.back.gaon.domain.schedule.entity.ScheduleTemplateVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleTemplateVersionRepository extends JpaRepository<ScheduleTemplateVersion, Long> {
    List<ScheduleTemplateVersion> findByTemplateId(Long templateId);
}