package com.back.gaon.domain.schedule.version.service;

import com.back.gaon.domain.schedule.version.dto.request.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionDetailResponse;

import java.util.List;

public interface ScheduleTemplateVersionService {
    ScheduleTemplateVersionCreateResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */);
    ScheduleTemplateVersionDetailResponse findVersionById(Long id);
    List<ScheduleTemplateVersionDetailResponse> findByTemplateId(Long templateId);
    ScheduleTemplateVersionDetailResponse findByTemplateAndId(Long templateId, Long versionId);

    /**
     * ✅ 버전 승인
     * - 해당 버전의 status를 APPROVED로 변경
     * - 부모 템플릿의 currentApprovedVersionId를 이 버전 id로 설정
     * - 템플릿 status도 APPROVED로 변경 (필요시)
     */
    ScheduleTemplateVersionDetailResponse approve(Long versionId);

    /**
     * ✅ 버전 반려
     * - 해당 버전의 status를 REJECTED로 변경
     * - rejectReason 저장
     * - 템플릿의 currentApprovedVersionId는 변경하지 않음
     */
    ScheduleTemplateVersionDetailResponse reject(Long versionId, String rejectReason);
}