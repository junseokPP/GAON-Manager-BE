package com.back.gaon.domain.schedule.schedule.controller;

import com.back.gaon.domain.schedule.schedule.dto.response.ScheduleResponse;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.schedule.mapper.ScheduleMapper;
import com.back.gaon.domain.schedule.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 🔥 승인된 템플릿 버전 기준으로 스케줄 생성
     * 예: POST /api/v1/schedules/generate?versionId=3&from=2025-03-01&to=2025-03-31
     */
    @PostMapping("/generate")
    public ResponseEntity<List<Schedule>> generateSchedules(
            @RequestParam Long versionId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<Schedule> created = scheduleService.generateSchedulesFromVersion(versionId, from, to);
        return ResponseEntity.ok(created);
    }

    /**
     * 🔥 특정 학생의 스케줄 조회
     * 예: GET /api/v1/schedules?memberId=1&from=2025-03-01&to=2025-03-31
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules(
            @RequestParam Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<Schedule> schedules = scheduleService.getSchedules(memberId, from, to);

        List<ScheduleResponse> responses = schedules.stream()
                .map(ScheduleMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }
}