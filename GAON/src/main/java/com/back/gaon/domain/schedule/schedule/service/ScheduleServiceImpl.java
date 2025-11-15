package com.back.gaon.domain.schedule.schedule.service;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.schedule.item.entity.ScheduleTemplateItem;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.schedule.enums.ScheduleStatus;
import com.back.gaon.domain.schedule.schedule.repository.ScheduleRepository;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.version.repository.ScheduleTemplateVersionRepository;
import com.back.gaon.domain.schedule.item.repository.ScheduleTemplateItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleTemplateVersionRepository scheduleTemplateVersionRepository;
    private final ScheduleTemplateItemRepository scheduleTemplateItemRepository;

    @Override
    public List<Schedule> generateSchedulesFromVersion(Long templateVersionId,
                                                       LocalDate from,
                                                       LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from 날짜는 to 날짜보다 이후일 수 없습니다.");
        }

        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(templateVersionId)
                .orElseThrow(() -> new EntityNotFoundException("템플릿 버전을 찾을 수 없습니다. id=" + templateVersionId));

        if (version.getStatus() != TemplateStatus.APPROVED) {
            throw new IllegalStateException("스케줄 생성은 APPROVED 상태의 버전에서만 가능합니다. 현재 상태: " + version.getStatus());
        }

        List<ScheduleTemplateItem> items = scheduleTemplateItemRepository.findByVersion_Id(templateVersionId);
        if (items.isEmpty()) {
            return List.of();
        }

        Member member = version.getTemplate().getMember();

        // ✅ 이 학생의 기존 스케줄 싹 지우고 다시 생성
        scheduleRepository.deleteByMemberIdAndDateBetween(member.getId(), from, to);

        List<Schedule> toSave = new ArrayList<>();
        LocalDate current = from;

        while (!current.isAfter(to)) {
            DayOfWeek dayOfWeek = current.getDayOfWeek();

            for (ScheduleTemplateItem item : items) {
                if (item.getDayOfWeek() != dayOfWeek) continue;

                Schedule schedule = Schedule.builder()
                        .member(member)
                        .templateVersion(version)
                        .templateItem(item)
                        .date(current)
                        .startTime(item.getStartTime())
                        .endTime(item.getEndTime())
                        .subject(item.getSubject())
                        .memo(null)
                        .status(ScheduleStatus.NORMAL)
                        .build();

                toSave.add(schedule);
            }

            current = current.plusDays(1);
        }

        if (toSave.isEmpty()) {
            return List.of();
        }
        return scheduleRepository.saveAll(toSave);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedules(Long memberId, LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from 날짜는 to 날짜보다 이후일 수 없습니다.");
        }

        return scheduleRepository.findByMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(
                memberId, from, to
        );
    }
}