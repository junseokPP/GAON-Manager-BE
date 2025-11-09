// src/main/java/com/back/gaon/domain/schedule/controller/ScheduleTemplateController.java
package com.back.gaon.domain.schedule.controller;

import com.back.gaon.domain.schedule.dto.request.template.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.dto.response.template.ScheduleTemplateResponse;
import com.back.gaon.domain.schedule.service.template.ScheduleTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // (나중에 Security 붙이면 활성화)
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-templates")
public class ScheduleTemplateController {

    private final ScheduleTemplateService scheduleTemplateService;

    /**
     * 현재: 인증/권한 없이 사용 가능
     * 나중에: ADMIN/STUDENT 제한, 소유자 체크, 관리자 즉시 승인 로직 활성화 예정
     */
    @PostMapping
    // @PreAuthorize("hasAnyRole('ADMIN','STUDENT')") // (Security 추가 시 주석 해제)
    public ResponseEntity<ScheduleTemplateResponse> create(
            @Valid @RequestBody ScheduleTemplateCreateRequest req
            // , Authentication auth // (Security 추가 시 주석 해제)
    ) {
        // 현재는 학생 플로우만: submit=true -> PENDING, false/null -> DRAFT
        // 나중에 Security 추가 시, 관리자면 즉시 APPROVED 로 변경
        ScheduleTemplateResponse created = scheduleTemplateService.create(req /*, auth */);
        return ResponseEntity
                .created(URI.create("/api/schedule-templates/" + created.id()))
                .body(created);
    }
}