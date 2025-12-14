package com.back.gaon.domain.schedule.controller;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.schedule.dto.ScheduleCreateRequest;
import com.back.gaon.domain.schedule.dto.ScheduleResponse;
import com.back.gaon.domain.schedule.dto.StudentScheduleResponse;
import com.back.gaon.domain.schedule.service.StudentScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student/schedules")
public class StudentScheduleController {

    private final StudentScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<StudentScheduleResponse> create(
            @AuthenticationPrincipal Member student,
            @RequestBody @Valid ScheduleCreateRequest request
    ) {
        return ResponseEntity.ok(
                scheduleService.createSchedule(student.getId(), request)
        );
    }

    @GetMapping
    public ResponseEntity<List<StudentScheduleResponse>> getMySchedules(
            @AuthenticationPrincipal Member student
    ) {
        return ResponseEntity.ok(
                scheduleService.getSchedulesByStudent(student.getId())
        );
    }

}
