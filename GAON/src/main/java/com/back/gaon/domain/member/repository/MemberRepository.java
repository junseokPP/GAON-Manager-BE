package com.back.gaon.domain.member.repository;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 전화번호로 회원 찾기 (로그인, 중복 체크)
     */
    Optional<Member> findByPhone(String phone);

    /**
     * 전화번호 중복 체크
     */
    boolean existsByPhone(String phone);

    /**
     * 역할별 회원 조회
     */
    List<Member> findByRole(Role role);

    /**
     * 역할 + 상태로 회원 조회
     */
    List<Member> findByRoleAndStatus(Role role, com.back.gaon.domain.member.enums.MemberStatus status);

    /**
     * setupToken으로 회원 찾기 (학부모 비밀번호 설정용)
     */
    Optional<Member> findBySetupToken(String setupToken);
}