package com.back.gaon.domain.schedule.version.service;

import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.domain.schedule.template.repository.ScheduleTemplateRepository;
import com.back.gaon.domain.schedule.version.dto.request.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.version.dto.response.ScheduleTemplateVersionDetailResponse;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.version.mapper.ScheduleTemplateVersionMapper;
import com.back.gaon.domain.schedule.version.repository.ScheduleTemplateVersionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleTemplateVersionServiceImpl implements ScheduleTemplateVersionService {

    private final ScheduleTemplateVersionRepository scheduleTemplateVersionRepository;
    private final ScheduleTemplateRepository templateRepo;

    @Override
    public ScheduleTemplateVersionCreateResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */) {
        // 1) 템플릿 존재 확인
        ScheduleTemplate template = templateRepo.findById(req.templateId())
                .orElseThrow(() -> new EntityNotFoundException("Template not found: id=" + req.templateId()));

        // 2) 동시에 PENDING 버전 1개만 허용 (권장 정책)
        if (Boolean.TRUE.equals(req.submit())
                && scheduleTemplateVersionRepository.existsByTemplateIdAndStatus(template.getId(), TemplateStatus.PENDING)) {
            throw new DataIntegrityViolationException("이미 승인 대기(PENDING) 중인 버전이 존재합니다.");
        }

        // 3) 버전 번호 결정 (요청이 null이면 자동 채번)
        int versionNo = (req.versionNo() != null)
                ? req.versionNo()
                : scheduleTemplateVersionRepository.findTopByTemplateIdOrderByVersionNoDesc(template.getId())
                .map(v -> v.getVersionNo() + 1)
                .orElse(1);

        // 4) 상태 결정: 지금은 학생 플로우만 (시큐리티 붙으면 관리자 즉시 APPROVED)
        TemplateStatus status = Boolean.TRUE.equals(req.submit())
                ? TemplateStatus.PENDING
                : TemplateStatus.DRAFT;

        // 5) 엔티티 생성/저장
        ScheduleTemplateVersion entity = ScheduleTemplateVersionMapper.toEntity(req, template, versionNo, status);
        ScheduleTemplateVersion saved = scheduleTemplateVersionRepository.save(entity);

        // 6) 응답
        return ScheduleTemplateVersionMapper.toCreateResponse(saved);
    }

    @Override
    public ScheduleTemplateVersionDetailResponse findVersionById(Long id){
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule template version not found: id=" + id));

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }

    @Override
    public List<ScheduleTemplateVersionDetailResponse> findByTemplateId(Long templateId) {
        List<ScheduleTemplateVersion> versions =
                scheduleTemplateVersionRepository.findByTemplateIdOrderByVersionNoDesc(templateId);

        return versions.stream()
                .map(ScheduleTemplateVersionMapper::toVersionDetailResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleTemplateVersionDetailResponse findByTemplateAndId(Long templateId, Long versionId) {
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(versionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Schedule template version not found: id=" + versionId
                ));

        // 🔥 소속 검증: 이 버전이 정말 해당 템플릿의 것인지
        if (!version.getTemplate().getId().equals(templateId)) {
            // 템플릿-버전 조합이 잘못된 경우 → 404로 숨기는 게 더 자연스럽다
            throw new EntityNotFoundException(
                    "Schedule template version not found for templateId=" + templateId + ", versionId=" + versionId
            );
        }

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }

    @Override
    public ScheduleTemplateVersionDetailResponse approve(Long versionId) {
        // 1) 버전 조회 (없으면 404)
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(versionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Schedule template version not found: id=" + versionId
                ));

        // 2) 상태 체크 (PENDING만 승인 허용)
        if (version.getStatus() != TemplateStatus.PENDING) {
            throw new IllegalStateException(
                    "승인은 PENDING 상태에서만 가능합니다. 현재 상태: " + version.getStatus()
            );
        }

        // 3) 상태 변경
        version.setStatus(TemplateStatus.APPROVED);
        version.setRejectReason(null);          // 이전 반려 사유 있으면 초기화
        // 나중에 Security 붙이면 여기서 reviewedBy, reviewedAt 같은 거 채우면 됨
        // version.setReviewedBy(currentAdminId);
        // version.setReviewedAt(LocalDateTime.now());

        // 4) 부모 템플릿도 업데이트
        ScheduleTemplate template = version.getTemplate();
        template.setCurrentApprovedVersionId(version.getId());
        template.setStatus(TemplateStatus.APPROVED);

        // 5) 트랜잭션 안이므로 save 명시 안 해도 flush 되지만, 명시하고 싶으면:
        // scheduleTemplateVersionRepository.save(version);

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }

    @Override
    public ScheduleTemplateVersionDetailResponse reject(Long versionId, String rejectReason) {
        // 1) 버전 조회
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(versionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Schedule template version not found: id=" + versionId
                ));

        // 2) 상태 체크 (PENDING만 반려 허용)
        if (version.getStatus() != TemplateStatus.PENDING) {
            throw new IllegalStateException(
                    "반려는 PENDING 상태에서만 가능합니다. 현재 상태: " + version.getStatus()
            );
        }

        // 3) 상태 변경
        version.setStatus(TemplateStatus.REJECTED);
        version.setRejectReason(rejectReason);
        // 나중에 Security 붙이면 reviewer 정보 채우면 됨
        // version.setReviewedBy(currentAdminId);
        // version.setReviewedAt(LocalDateTime.now());

        // 4) 템플릿은 여기서 currentApprovedVersionId를 건드리지 않음
        //    이미 승인된 버전이 있다면 그대로 유지하는 게 자연스러움.

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }
}
