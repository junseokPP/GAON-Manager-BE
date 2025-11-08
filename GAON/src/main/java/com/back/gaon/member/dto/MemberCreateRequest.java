package com.back.gaon.member.dto;

import com.back.gaon.member.enums.Gender;
import com.back.gaon.member.enums.Grade;
import com.back.gaon.member.enums.MemberStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberCreateRequest(

        @NotBlank(message = "필수 입력입니다.")
        String name,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender, // 선택 입력

        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^[0-9\\-]{9,15}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
        String phone,

        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^[0-9\\-]{9,15}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
        String parentPhone,

        @NotBlank(message = "필수 입력입니다.")
        String school,

        @NotNull(message = "학년은 필수입니다.")
        Grade grade

        ) {
}
