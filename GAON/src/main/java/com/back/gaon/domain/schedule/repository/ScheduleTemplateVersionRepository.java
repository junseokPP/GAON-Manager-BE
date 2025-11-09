package com.back.gaon.domain.schedule.repository;

import com.back.gaon.domain.schedule.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.enums.TemplateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleTemplateVersionRepository extends JpaRepository<ScheduleTemplateVersion, Long> {
    boolean existsByTemplateIdAndStatus(Long templateId, TemplateStatus status);
    Optional<ScheduleTemplateVersion> findTopByTemplateIdOrderByVersionNoDesc(Long templateId);
}