package com.back.gaon.domain.schedule.controller;

import com.back.gaon.domain.schedule.dto.ScheduleResponse;
import com.back.gaon.domain.schedule.dto.ScheduleWithAttendanceResponse;
import com.back.gaon.domain.schedule.dto.StudentScheduleResponse;
import com.back.gaon.domain.schedule.service.AdminScheduleService;
import com.back.gaon.domain.schedule.service.StudentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/schedules")
public class AdminScheduleController {

    private final AdminScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleWithAttendanceResponse>> getByDay(
            @RequestParam DayOfWeek day
    ) {
        return ResponseEntity.ok(scheduleService.getSchedulesByDay(day));
    }

    @PostMapping("/{scheduleId}/approve")
    public ResponseEntity<StudentScheduleResponse> approve(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.approveSchedule(scheduleId));
    }
}
