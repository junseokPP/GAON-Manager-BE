package com.back.gaon.domain.schedule.item.repository;

import com.back.gaon.domain.schedule.item.entity.ScheduleTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleTemplateItemRepository extends JpaRepository<ScheduleTemplateItem, Long> {

    List<ScheduleTemplateItem> findByVersion_Id(Long versionId);

}