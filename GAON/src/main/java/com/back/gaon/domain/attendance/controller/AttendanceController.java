package com.back.gaon.domain.attendance.controller;

import com.back.gaon.domain.attendance.dto.response.AttendanceResponse;
import com.back.gaon.domain.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * ✅ 등원
     */
    @PostMapping("/{studentId}/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.checkIn(studentId));
    }

    /**
     * ✅ 하원
     */
    @PostMapping("/{studentId}/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.checkOut(studentId));
    }

    /**
     * ✅ 외출 시작
     */
    @PostMapping("/{studentId}/outing/start")
    public ResponseEntity<AttendanceResponse> startOuting(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.outingStart(studentId));
    }

    /**
     * ✅ 외출 복귀
     */
    @PostMapping("/{studentId}/outing/end")
    public ResponseEntity<AttendanceResponse> endOuting(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.outingReturn(studentId));
    }

    /**
     * ✅ 통보지각
     */
    @PostMapping("/{studentId}/excuse-late")
    public ResponseEntity<AttendanceResponse> markExcuseLate(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.excuseLate(studentId));
    }

    /**
     * ✅ 통보결석
     */
    @PostMapping("/{studentId}/excuse-absent")
    public ResponseEntity<AttendanceResponse> markExcuseAbsent(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(attendanceService.excuseAbsent(studentId));
    }

    @GetMapping("/today")
    public ResponseEntity<List<AttendanceResponse>> getTodayAttendance() {
        return ResponseEntity.ok(attendanceService.getTodayAttendance());
    }

}
