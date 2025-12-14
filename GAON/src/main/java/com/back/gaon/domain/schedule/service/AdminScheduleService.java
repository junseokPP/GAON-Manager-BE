package com.back.gaon.domain.schedule.service;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.repository.AttendanceRepository;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.repository.OutingRepository;
import com.back.gaon.domain.schedule.dto.ScheduleResponse;
import com.back.gaon.domain.schedule.dto.ScheduleWithAttendanceResponse;
import com.back.gaon.domain.schedule.dto.StudentScheduleResponse;
import com.back.gaon.domain.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.enums.ScheduleStatus;
import com.back.gaon.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static com.back.gaon.domain.schedule.enums.ScheduleStatus.APPROVED;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AttendanceRepository attendanceRepository;
    private final OutingRepository outingRepository;

    public List<ScheduleWithAttendanceResponse> getSchedulesByDay(DayOfWeek day) {

        List<Schedule> schedules = scheduleRepository.findByDayAndStatus(day, APPROVED);

        return schedules.stream()
                .map(schedule -> {
                    Attendance attendance =
                            attendanceRepository.findByStudentIdAndDate(
                                    schedule.getStudent().getId(),
                                    LocalDate.now()
                            ).orElse(null);

                    return ScheduleWithAttendanceResponse.from(schedule, attendance);
                })
                .toList();
    }

    public StudentScheduleResponse approveSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("스케줄을 찾을 수 없습니다."));

        schedule.setStatus(APPROVED);

        List<Outing> outings = outingRepository.findByScheduleId(schedule.getId());

        return StudentScheduleResponse.from(schedule, outings);
    }

    public StudentScheduleResponse rejectSchedule(Long scheduleId, String reason) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("스케줄을 찾을 수 없습니다."));

        schedule.setStatus(ScheduleStatus.REJECTED);

        List<Outing> outings = outingRepository.findByScheduleId(schedule.getId());

        return StudentScheduleResponse.from(schedule, outings);
    }

}
