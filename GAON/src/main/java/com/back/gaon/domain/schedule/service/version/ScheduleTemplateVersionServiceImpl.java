package com.back.gaon.domain.schedule.service.version;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionResponse;
import com.back.gaon.domain.schedule.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.enums.TemplateStatus;
import com.back.gaon.domain.schedule.mapper.ScheduleTemplateVersionMapper;
import com.back.gaon.domain.schedule.repository.ScheduleTemplateRepository;
import com.back.gaon.domain.schedule.repository.ScheduleTemplateVersionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleTemplateVersionServiceImpl implements ScheduleTemplateVersionService {

    private final ScheduleTemplateRepository templateRepo;
    private final ScheduleTemplateVersionRepository versionRepo;

    @Override
    public ScheduleTemplateVersionResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */) {
        // 1) 템플릿 존재 확인
        ScheduleTemplate template = templateRepo.findById(req.templateId())
                .orElseThrow(() -> new EntityNotFoundException("Template not found: id=" + req.templateId()));

        // 2) 동시에 PENDING 버전 1개만 허용 (권장 정책)
        if (Boolean.TRUE.equals(req.submit())
                && versionRepo.existsByTemplateIdAndStatus(template.getId(), TemplateStatus.PENDING)) {
            throw new DataIntegrityViolationException("이미 승인 대기(PENDING) 중인 버전이 존재합니다.");
        }

        // 3) 버전 번호 결정 (요청이 null이면 자동 채번)
        int versionNo = (req.versionNo() != null)
                ? req.versionNo()
                : versionRepo.findTopByTemplateIdOrderByVersionNoDesc(template.getId())
                .map(v -> v.getVersionNo() + 1)
                .orElse(1);

        // 4) 상태 결정: 지금은 학생 플로우만 (시큐리티 붙으면 관리자 즉시 APPROVED)
        TemplateStatus status = Boolean.TRUE.equals(req.submit())
                ? TemplateStatus.PENDING
                : TemplateStatus.DRAFT;

        // 5) 엔티티 생성/저장
        ScheduleTemplateVersion entity = ScheduleTemplateVersionMapper.toEntity(req, template, versionNo, status);
        ScheduleTemplateVersion saved = versionRepo.save(entity);

        // 6) 응답
        return ScheduleTemplateVersionMapper.toResponse(saved);
    }
}