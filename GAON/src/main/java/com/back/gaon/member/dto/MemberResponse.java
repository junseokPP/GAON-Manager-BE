package com.back.gaon.member.dto;

import com.back.gaon.member.enums.Gender;
import com.back.gaon.member.enums.Grade;
import com.back.gaon.member.enums.MemberStatus;

import java.time.LocalDate;

public record MemberResponse(
        Long id,                // 회원 고유 ID
        String name,            // 이름
        Gender gender,          // 성별 (M/F)
        String phone,           // 학생 연락처
        String parentPhone,     // 학부모 연락처
        String school,          // 학교명
        Grade grade,            // 학년
        MemberStatus status,    // 회원 상태 (ACTIVE, INACTIVE 등)
        LocalDate createdDate   // 등록일 (엔티티에 createdAt 있으면)
) {}
