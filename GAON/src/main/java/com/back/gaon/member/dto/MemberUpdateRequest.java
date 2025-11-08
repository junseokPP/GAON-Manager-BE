package com.back.gaon.member.dto;

import com.back.gaon.member.enums.Gender;
import com.back.gaon.member.enums.Grade;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequest(

        @Size(max = 50, message = "이름은 최대 50자까지 가능합니다.")
        String name,

        Gender gender,

        @Pattern(regexp = "^[0-9\\-]{9,15}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
        String phone,

        @Pattern(regexp = "^[0-9\\-]{9,15}$", message = "학부모 번호 형식이 올바르지 않습니다.")
        String parentPhone,

        @Size(max = 50, message = "학교명은 최대 50자까지 가능합니다.")
        String school,

        Grade grade
) {}