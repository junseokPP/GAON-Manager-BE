package com.back.gaon.domain.member.entity;
import java.time.LocalDate;

import com.back.gaon.domain.member.enums.Grade;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
/**
 * 학생 상세 정보
 * - Member(role=STUDENT)와 1:1 관계
 * - 학생에게만 필요한 정보 (학교, 학년 등)
 * - parent: 학부모와 N:1 관계 (한 학부모가 2~3명 자녀 가능)
 */
@Entity
@Table(name = "student_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Member의 PK를 그대로 사용

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50)
    private String school;  // 학교명

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Grade grade;    // 학년

    // 🔥 학부모와 N:1 관계 (한 학부모가 여러 자녀 가능)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Member parent;  // role = PARENT인 Member

    @Column(name = "emergency_contact", length = 20)
    private String emergencyContact;  // 비상 연락처

    @Column(length = 255)
    private String memo;  // 특이사항

    // 🔥 추가 필드
    @Column(name = "seat_number", length = 10)
    private String seatNumber;  // 좌석 번호 (예: A-01, B-12)

    @Column(name = "registration_date")
    private LocalDate registrationDate;  // 등록일

    @Column(name = "expiration_date")
    private LocalDate expirationDate;  // 만료일 (수강권 만료)
}