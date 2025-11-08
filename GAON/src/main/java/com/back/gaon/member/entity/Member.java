package com.back.gaon.member.entity;

import com.back.gaon.member.enums.Gender;
import com.back.gaon.member.enums.Grade;
import com.back.gaon.member.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 회원 고유 ID

    @Column(nullable = false, length = 50)
    private String name;  // 이름

    @Enumerated(EnumType.STRING)
    private Gender gender;  // 성별 (M/F)

    @Column(nullable = false, length = 20, unique = true)
    private String phone;  // 학생 휴대폰

    @Column(length = 20)
    private String parentPhone;  // 학부모 연락처

    @Column(length = 50)
    private String school;  // 학교명

    @Enumerated(EnumType.STRING)
    private Grade grade;  // 학년

    private LocalDate joinDate;  // 등록일

    @Enumerated(EnumType.STRING)
    private MemberStatus status;  // active(현재이용중) / inactive(임시중단) / suspended(퇴원)

    private LocalDate createdAt = LocalDate.now();  // 생성일
}
