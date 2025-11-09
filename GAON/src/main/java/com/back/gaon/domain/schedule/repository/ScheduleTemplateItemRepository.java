package com.back.gaon.domain.schedule.repository;

import com.back.gaon.domain.schedule.entity.ScheduleTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleTemplateItemRepository extends JpaRepository<ScheduleTemplateItem, Long> {
    List<ScheduleTemplateItem> findByVersionId(Long versionId);
    List<ScheduleTemplateItem> findByVersionIdOrderByDayOfWeekAscStartTimeAsc(Long versionId);
}