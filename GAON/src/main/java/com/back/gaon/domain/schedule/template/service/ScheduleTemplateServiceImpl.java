package com.back.gaon.domain.schedule.template.service;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.schedule.template.dto.request.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateCreateResponse;
import com.back.gaon.domain.schedule.template.dto.response.ScheduleTemplateDetailResponse;
import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.domain.schedule.template.mapper.ScheduleTemplateMapper;
import com.back.gaon.domain.schedule.template.repository.ScheduleTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleTemplateServiceImpl implements ScheduleTemplateService {

    private final ScheduleTemplateRepository scheduleTemplateRepository;
    private final MemberRepository memberRepository;

    @Override
    public ScheduleTemplateCreateResponse create(ScheduleTemplateCreateRequest req /*, Authentication auth */){
        Member member = memberRepository.findById(req.memberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found: id=" + req.memberId()));

        if(scheduleTemplateRepository.existsByMemberId(member.getId())){
            throw new DataIntegrityViolationException("이 멤버는 이미 템플릿을 보유하고 있습니다.");
        }

        ScheduleTemplate entity = ScheduleTemplateMapper.toEntity(req, member);

        // 4) 현재 단계의 상태 결정 (학생 플로우만 활성)
        //    - submit=true  -> PENDING
        //    - submit=false -> DRAFT
        //    - 관리자 즉시 APPROVED는 Security 도입 후 활성화 예정
        boolean submit = Boolean.TRUE.equals(req.submit());
        entity.setStatus(submit ? TemplateStatus.PENDING : TemplateStatus.DRAFT);
        entity.setApprovedBy(null);
        entity.setApprovedAt(null);

        // (Security 도입 후 활성화 코드 예시)
        // boolean isAdmin = hasRole(auth, "ROLE_ADMIN");
        // boolean isStudent = hasRole(auth, "ROLE_STUDENT");
        // if (isAdmin) {
        //     entity.setStatus(TemplateStatus.APPROVED);
        //     entity.setApprovedBy(extractUserIdFrom(auth));
        //     entity.setApprovedAt(java.time.LocalDateTime.now());
        // } else if (isStudent) {
        //     Long currentUserMemberId = extractMemberIdFrom(auth);
        //     if (!java.util.Objects.equals(currentUserMemberId, req.memberId())) {
        //         throw new AccessDeniedException("학생은 본인 템플릿만 생성할 수 있습니다.");
        //     }
        //     entity.setStatus(submit ? TemplateStatus.PENDING : TemplateStatus.DRAFT);
        // } else {
        //     throw new AccessDeniedException("생성 권한이 없습니다.");
        // }

        ScheduleTemplate saved = scheduleTemplateRepository.save(entity);
        return ScheduleTemplateMapper.toCreateResponse(saved);
    }

    @Override
    public ScheduleTemplateDetailResponse  findById(Long id) {
        ScheduleTemplate scheduleTemplate = scheduleTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule template not found: id=" + id));
        return ScheduleTemplateMapper.toDetailResponse(scheduleTemplate);
    }

    @Override
    public List<ScheduleTemplateDetailResponse> findAll(){
        List<ScheduleTemplate> scheduleTemplates = scheduleTemplateRepository.findAll();
        List<ScheduleTemplateDetailResponse> scheduleTemplateDetailResponses = new ArrayList<>();
        for (ScheduleTemplate scheduleTemplate : scheduleTemplates) {
            scheduleTemplateDetailResponses.add(ScheduleTemplateMapper.toDetailResponse(scheduleTemplate));
        }
        return scheduleTemplateDetailResponses;
    }
}
