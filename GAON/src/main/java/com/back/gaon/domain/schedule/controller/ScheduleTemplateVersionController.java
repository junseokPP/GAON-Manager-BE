// src/main/java/com/back/gaon/domain/schedule/controller/ScheduleTemplateVersionController.java
package com.back.gaon.domain.schedule.controller;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionResponse;
import com.back.gaon.domain.schedule.service.version.ScheduleTemplateVersionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-template-versions")
public class ScheduleTemplateVersionController {

    private final ScheduleTemplateVersionService versionService;

    @PostMapping
    // @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<ScheduleTemplateVersionResponse> create(
            @Valid @RequestBody ScheduleTemplateVersionCreateRequest req
            // , Authentication auth
    ) {
        ScheduleTemplateVersionResponse created = versionService.create(req /*, auth */);
        return ResponseEntity.created(URI.create("/api/schedule-template-versions/" + created.id()))
                .body(created);
    }
}