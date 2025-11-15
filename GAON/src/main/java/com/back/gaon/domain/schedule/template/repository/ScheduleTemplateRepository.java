package com.back.gaon.domain.schedule.template.repository;

import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleTemplateRepository extends JpaRepository<ScheduleTemplate, Long> {
    Optional<ScheduleTemplate> findByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);
}
