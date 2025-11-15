// src/main/java/com/back/gaon/domain/schedule/controller/ScheduleTemplateController.java
package com.back.gaon.domain.schedule.template.controller;

import com.back.gaon.domain.schedule.template.dto.request.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateCreateResponse;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateDetailResponse;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionDetailResponse;
import com.back.gaon.domain.schedule.template.service.ScheduleTemplateService;
import com.back.gaon.domain.schedule.version.service.ScheduleTemplateVersionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule-templates")
public class ScheduleTemplateController {

    private final ScheduleTemplateService scheduleTemplateService;
    private final ScheduleTemplateVersionService scheduleTemplateVersionService;
    /**
     * 현재: 인증/권한 없이 사용 가능
     * 나중에: ADMIN/STUDENT 제한, 소유자 체크, 관리자 즉시 승인 로직 활성화 예정
     */
    @PostMapping
    // @PreAuthorize("hasAnyRole('ADMIN','STUDENT')") // (Security 추가 시 주석 해제)
    public ResponseEntity<ScheduleTemplateCreateResponse> create(
            @Valid @RequestBody ScheduleTemplateCreateRequest req
            // , Authentication auth // (Security 추가 시 주석 해제)
    ) {
        // 현재는 학생 플로우만: submit=true -> PENDING, false/null -> DRAFT
        // 나중에 Security 추가 시, 관리자면 즉시 APPROVED 로 변경
        ScheduleTemplateCreateResponse created = scheduleTemplateService.create(req /*, auth */);
        return ResponseEntity
                .created(URI.create("/api/schedule-templates/" + created.id()))
                .body(created);
    }

    // @PreAuthorize("hasAnyRole('ADMIN')") // (Security 추가 시 주석 해제)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleTemplateDetailResponse> get(@PathVariable Long id) {
        ScheduleTemplateDetailResponse detailTemplate = scheduleTemplateService.findById(id);
        return ResponseEntity.ok(detailTemplate);
    }

    // @PreAuthorize("hasAnyRole('ADMIN')") // (Security 추가 시 주석 해제)
    @GetMapping
    public ResponseEntity<List<ScheduleTemplateDetailResponse>> getAll() {
        List<ScheduleTemplateDetailResponse> templates = scheduleTemplateService.findAll();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{templateId}/versions")
    public ResponseEntity<List<ScheduleTemplateVersionDetailResponse>> getVersionsByTemplate(
            @PathVariable Long templateId
    ) {
        List<ScheduleTemplateVersionDetailResponse> versions =
                scheduleTemplateVersionService.findByTemplateId(templateId);

        return ResponseEntity.ok(versions);
    }

    @GetMapping("/{templateId}/versions/{versionId}")
    public ResponseEntity<ScheduleTemplateVersionDetailResponse> getVersionOfTemplate(
            @PathVariable Long templateId,
            @PathVariable Long versionId
    ) {
        ScheduleTemplateVersionDetailResponse detail =
                scheduleTemplateVersionService.findByTemplateAndId(templateId, versionId);

        return ResponseEntity.ok(detail);
    }
}