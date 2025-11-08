package com.back.gaon.member.service;

import com.back.gaon.member.dto.MemberCreateRequest;
import com.back.gaon.member.dto.MemberResponse;
import com.back.gaon.member.dto.MemberUpdateRequest;
import com.back.gaon.member.entity.Member;
import com.back.gaon.member.enums.MemberStatus;
import com.back.gaon.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse createMember(MemberCreateRequest request) {
        Member member = toEntity(request);
        Member saved = memberRepository.save(member);
        return toResponse(saved);
    }

    // 추후에 키워드 파라미터 추가해보자
    public List<MemberResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(this::toResponse)
                .toList();
    }

    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        return toResponse(member);
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberUpdateRequest req) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (req.name() != null) member.setName(req.name());
        if (req.gender() != null) member.setGender(req.gender());
        if (req.phone() != null) member.setPhone(req.phone());
        if (req.parentPhone() != null) member.setParentPhone(req.parentPhone());
        if (req.school() != null) member.setSchool(req.school());
        if (req.grade() != null) member.setGrade(req.grade());

        return toResponse(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        member.setStatus(MemberStatus.INACTIVE);
    }

    //  요청 DTO → 엔티티
    private Member toEntity(MemberCreateRequest request) {
        Member member = new Member();
        member.setName(request.name());
        member.setGender(request.gender());
        member.setPhone(request.phone());
        member.setParentPhone(request.parentPhone());
        member.setSchool(request.school());
        member.setGrade(request.grade());
        member.setStatus(MemberStatus.ACTIVE); // 기본값 서비스에서 처리
        return member;
    }

    //  엔티티 → 응답 DTO
    private MemberResponse toResponse(Member m) {
        return new MemberResponse(
                m.getId(),
                m.getName(),
                m.getGender(),
                m.getPhone(),
                m.getParentPhone(),
                m.getSchool(),
                m.getGrade(),
                m.getStatus(),
                m.getCreatedAt() != null ? m.getCreatedAt() : null
        );
    }


}

