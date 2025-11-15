package com.back.gaon.domain.schedule.version.repository;

import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleTemplateVersionRepository extends JpaRepository<ScheduleTemplateVersion, Long> {
    boolean existsByTemplateIdAndStatus(Long templateId, TemplateStatus status);
    Optional<ScheduleTemplateVersion> findTopByTemplateIdOrderByVersionNoDesc(Long templateId);
    List<ScheduleTemplateVersion> findByTemplateIdOrderByVersionNoDesc(Long templateId);
}