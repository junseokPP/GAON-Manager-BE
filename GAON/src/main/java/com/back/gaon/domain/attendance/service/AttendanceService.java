package com.back.gaon.domain.attendance.service;

import com.back.gaon.domain.attendance.dto.response.AttendanceResponse;
import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.entity.AttendancePenaltyLog;
import com.back.gaon.domain.attendance.entity.OutingLog;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import com.back.gaon.domain.attendance.enums.PenaltyType;
import com.back.gaon.domain.attendance.repository.AttendancePenaltyLogRepository;
import com.back.gaon.domain.attendance.repository.AttendanceRepository;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final AttendancePenaltyLogRepository penaltyRepository;
    private final ScheduleRepository scheduleRepository;

    // ------------------------------------
    // 0. 오늘 출결 조회 또는 생성
    // ------------------------------------
    private Attendance getOrCreateToday(Long studentId) {
        LocalDate today = LocalDate.now();

        return attendanceRepository.findByStudentIdAndDate(studentId, today)
                .orElseGet(() -> attendanceRepository.save(
                        Attendance.builder()
                                .student(memberRepository.getReferenceById(studentId))
                                .date(today)
                                .day(today.getDayOfWeek())
                                .status(AttendanceStatus.NONE)
                                .build()
                ));
    }

    // ------------------------------------
    // 1. 등원 (check-in)
    // ------------------------------------
    public AttendanceResponse checkIn(Long studentId) {
        Attendance a = getOrCreateToday(studentId);

        // 이미 등원했다면 추가 등원 금지
        if (a.getAttendTime() != null) {
            throw new RuntimeException("이미 등원 처리되었습니다.");
        }

        LocalTime now = LocalTime.now();
        a.setAttendTime(now);
        a.setStatus(AttendanceStatus.PRESENT);

        // 스케줄 등원시간 가져오기
        Schedule schedule = scheduleRepository
                .findByStudentIdAndDay(studentId, LocalDate.now().getDayOfWeek())
                .orElse(null);

        LocalTime scheduledAttend = schedule != null ? schedule.getAttendTime() : null;

        // 지각 계산 (통보지각이 아닐 경우만)
        if (!a.isExcuseLate() && scheduledAttend != null) {
            if (now.isAfter(scheduledAttend.plusMinutes(30))) {

                // 무단지각 기록 저장
                penaltyRepository.save(
                        AttendancePenaltyLog.builder()
                                .attendance(a)
                                .type(PenaltyType.LATE_ABSENT)
                                .recordedAt(LocalDateTime.now())
                                .build()
                );
            }
        }

        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 2. 하원 (check-out)
    // ------------------------------------
    public AttendanceResponse checkOut(Long studentId) {
        Attendance a = getOrCreateToday(studentId);

        if (a.getAttendTime() == null) {
            throw new RuntimeException("등원하지 않았습니다.");
        }

        a.setLeaveTime(LocalTime.now());
        a.setStatus(AttendanceStatus.LEAVE);

        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 3. 외출 시작
    // ------------------------------------
    public AttendanceResponse outingStart(Long studentId) {
        Attendance a = getOrCreateToday(studentId);

        OutingLog log = OutingLog.builder()
                .attendance(a)
                .startTime(LocalTime.now())
                .build();

        a.getOutingLogs().add(log); // Cascade 덕분에 저장됨
        a.setStatus(AttendanceStatus.OUTING);

        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 4. 외출 복귀
    // ------------------------------------
    public AttendanceResponse outingReturn(Long studentId) {
        Attendance a = getOrCreateToday(studentId);

        // 가장 마지막 외출 로그 중 endTime이 비어있는 것 선택
        OutingLog lastLog = a.getOutingLogs().stream()
                .filter(l -> l.getEndTime() == null)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("진행중인 외출이 없습니다."));

        lastLog.setEndTime(LocalTime.now());
        a.setStatus(AttendanceStatus.PRESENT);

        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 5. 통보지각
    // ------------------------------------
    public AttendanceResponse excuseLate(Long studentId) {
        Attendance a = getOrCreateToday(studentId);
        a.setExcuseLate(true);
        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 6. 통보결석
    // ------------------------------------
    public AttendanceResponse excuseAbsent(Long studentId) {
        Attendance a = getOrCreateToday(studentId);
        a.setExcuseAbsent(true);
        return AttendanceResponse.from(a);
    }

    // ------------------------------------
    // 7. 무단결석 자동 처리 (23:00)
    // ------------------------------------
    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Seoul")
    public void autoMarkAbsent() {
        LocalDate today = LocalDate.now();

        List<Attendance> list = attendanceRepository.findAllByDate(today);

        for (Attendance a : list) {

            // 등원 없고, 통보결석도 아닌 경우 → 무단결석
            if (a.getAttendTime() == null && !a.isExcuseAbsent()) {

                penaltyRepository.save(
                        AttendancePenaltyLog.builder()
                                .attendance(a)
                                .type(PenaltyType.ABSENT)
                                .recordedAt(LocalDateTime.now())
                                .build()
                );

                a.setStatus(AttendanceStatus.ABSENT);
            }
        }
    }

    public List<AttendanceResponse> getTodayAttendance() {
        LocalDate today = LocalDate.now();

        List<Attendance> list = attendanceRepository.findAllByDate(today);

        return list.stream()
                .map(AttendanceResponse::from)
                .toList();
    }

}
