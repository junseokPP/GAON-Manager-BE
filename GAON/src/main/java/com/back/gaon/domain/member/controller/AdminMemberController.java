package com.back.gaon.domain.member.controller;

import com.back.gaon.domain.member.dto.*;
import com.back.gaon.domain.member.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 전용 - 회원 관리 컨트롤러
 * /api/v1/admin/members
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
public class AdminMemberController {

    private final AdminService adminService;

    /**
     * 🔥 학생 등록 (학부모 자동 생성 + 연결)
     * POST /api/v1/admin/members/students/create
     * 
     * 처리 내용:
     * 1. Student Member 생성
     * 2. StudentDetail 생성
     * 3. Parent Member 생성 (또는 기존 학부모 찾기)
     * 4. StudentDetail.parent 연결
     * 5. 학부모에게 비밀번호 설정 SMS 발송
     */

    @PostMapping("/students/create")
    public ResponseEntity<StudentCreateResponse> createStudent(
            @RequestBody @Valid StudentCreateRequest request
    ) {
        StudentCreateResponse response = adminService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 학부모 단독 등록 (드물게 사용)
     * POST /api/v1/admin/members/parents/create
     */
    @PostMapping("/parents/create")
    public ResponseEntity<MemberResponse> registerParent(
            @RequestBody @Valid ParentCreateRequest request
    ) {
        MemberResponse response = adminService.createParent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 전체 학생 조회 (필터링 가능)
     * GET /api/v1/admin/members/students?status=ACTIVE
     */
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(adminService.getAllStudents(status));
    }


    /**
     * 학생 상세 조회
     * GET /api/v1/admin/members/students/{id}
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getStudent(id));
    }


    /**
     * 학생 정보 수정
     * PUT /api/v1/admin/members/students/{id}
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentUpdateResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid StudentUpdateRequest request
    ) {
        return ResponseEntity.ok(adminService.updateStudent(id, request));
    }


    /**
     * 학생 비활성화
     * DELETE /api/v1/admin/members/students/{id}
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deactivateStudent(@PathVariable Long id) {
        adminService.deactivateStudent(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * 학부모-자녀 수동 연결
     * POST /api/v1/admin/members/link-parent-child
     */
    @PostMapping("/link-parent-child")
    public ResponseEntity<Void> linkParentChild(
            @RequestParam Long parentId,
            @RequestParam Long studentId
    ) {
        adminService.linkParentChild(parentId, studentId);
        return ResponseEntity.ok().build();
    }
}