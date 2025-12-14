package com.back.gaon.domain.member.repository;

import com.back.gaon.domain.member.entity.StudentDetail;
import com.back.gaon.domain.member.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail, Long> {

    /**
     * Member ID로 학생 상세 정보 조회
     */
    Optional<StudentDetail> findByMemberId(Long memberId);

    /**
     * 특정 학부모의 자녀 수 카운트
     * - 최대 3명 제한 체크용
     */
    long countByParentId(Long parentId);

    /**
     * 특정 학부모의 모든 자녀 조회
     */
    List<StudentDetail> findByParentId(Long parentId);

    /**
     * 학생이 특정 학부모의 자녀인지 확인
     */
    boolean existsByMemberIdAndParentId(Long memberId, Long parentId);
    
    /**
     * 좌석 번호 중복 체크
     */
    boolean existsBySeatNumber(String seatNumber);

    List<StudentDetail> findByMemberStatus(MemberStatus memberStatus);
}