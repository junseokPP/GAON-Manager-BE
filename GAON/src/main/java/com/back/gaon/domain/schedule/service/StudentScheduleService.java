package com.back.gaon.domain.schedule.service;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.outing.dto.OutingCreateRequest;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.repository.OutingRepository;
import com.back.gaon.domain.schedule.dto.ScheduleCreateRequest;
import com.back.gaon.domain.schedule.dto.ScheduleResponse;
import com.back.gaon.domain.schedule.dto.StudentScheduleResponse;
import com.back.gaon.domain.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.enums.ScheduleStatus;
import com.back.gaon.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final OutingRepository outingRepository;
    private final MemberRepository memberRepository;

    public StudentScheduleResponse createSchedule(Long studentId, ScheduleCreateRequest request) {
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다."));

        Schedule schedule = Schedule.builder()
                .student(student)
                .day(request.getDay())
                .attendTime(request.getAttendTime())
                .leaveTime(request.getLeaveTime())
                .memo(request.getMemo())
                .status(ScheduleStatus.PENDING) // 학생이 작성했으므로 대기 상태
                .build();

        Schedule saved = scheduleRepository.save(schedule);

        // 외출 저장
        List<Outing> outings = new ArrayList<>();
        if (request.getOutings() != null) {
            for (OutingCreateRequest o : request.getOutings()) {
                outings.add(outingRepository.save(
                        Outing.builder()
                                .schedule(saved)
                                .startTime(o.getStartTime())
                                .endTime(o.getEndTime())
                                .title(o.getTitle())
                                .build()
                ));
            }
        }

        return StudentScheduleResponse.from(saved, outings);
    }

    public List<StudentScheduleResponse> getSchedulesByStudent(Long studentId) {

        // 학생 스케줄 전체 조회
        List<Schedule> schedules = scheduleRepository.findByStudentId(studentId);

        // 각 스케줄의 외출 내용도 함께 조회
        return schedules.stream()
                .map(schedule -> {
                    List<Outing> outings = outingRepository.findByScheduleId(schedule.getId());
                    return StudentScheduleResponse.from(schedule, outings);
                })
                .toList();
    }

}
