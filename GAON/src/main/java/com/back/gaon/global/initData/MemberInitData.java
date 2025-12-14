package com.back.gaon.global.initData;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.entity.StudentDetail;
import com.back.gaon.domain.member.enums.Gender;
import com.back.gaon.domain.member.enums.Grade;
import com.back.gaon.domain.member.enums.MemberStatus;
import com.back.gaon.domain.member.enums.Role;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.member.repository.StudentDetailRepository;
import com.back.gaon.domain.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.enums.ScheduleStatus;
import com.back.gaon.domain.schedule.repository.ScheduleRepository;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.repository.OutingRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class MemberInitData {

    @Bean
    CommandLineRunner initMembers(
            MemberRepository memberRepository,
            StudentDetailRepository studentDetailRepository,
            PasswordEncoder passwordEncoder,
            ScheduleRepository scheduleRepository,
            OutingRepository outingRepository
    ) {
        return args -> {

            if (memberRepository.count() > 0) return;

            // -----------------------------
            // 1) 원장 생성
            // -----------------------------
            Member director = memberRepository.save(Member.builder()
                    .name("김원장")
                    .role(Role.DIRECTOR)
                    .phone("010-1000-0000")
                    .password(passwordEncoder.encode("director1234"))
                    .status(MemberStatus.ACTIVE)
                    .joinDate(LocalDate.now())
                    .build());

            // -----------------------------
            // 2) 관리자 생성
            // -----------------------------
            Member admin = memberRepository.save(Member.builder()
                    .name("이관리")
                    .role(Role.ADMIN)
                    .phone("010-2000-0000")
                    .password(passwordEncoder.encode("admin1234"))
                    .status(MemberStatus.ACTIVE)
                    .joinDate(LocalDate.now())
                    .build());

            // -----------------------------
            // 3) 학부모 생성
            // -----------------------------
            Member parent1 = memberRepository.save(Member.builder()
                    .name("박학부")
                    .role(Role.PARENT)
                    .phone("010-9999-8888")
                    .password(passwordEncoder.encode("parent1234"))
                    .status(MemberStatus.ACTIVE)
                    .passwordTemp(false)
                    .joinDate(LocalDate.now())
                    .build());

            // -----------------------------
            // 4) 학생들 생성
            // -----------------------------
            Member student1 = memberRepository.save(Member.builder()
                    .name("홍길동")
                    .role(Role.STUDENT)
                    .gender(Gender.Male)
                    .phone("010-1111-1111")
                    .password(passwordEncoder.encode("student1111"))
                    .status(MemberStatus.ACTIVE)
                    .joinDate(LocalDate.now())
                    .build());

            Member student2 = memberRepository.save(Member.builder()
                    .name("이영희")
                    .role(Role.STUDENT)
                    .gender(Gender.Female)
                    .phone("010-2222-2222")
                    .password(passwordEncoder.encode("student2222"))
                    .status(MemberStatus.ACTIVE)
                    .joinDate(LocalDate.now())
                    .build());

            // -----------------------------
            // 5) 학생 상세 정보 생성
            // -----------------------------
            studentDetailRepository.save(StudentDetail.builder()
                    .member(student1)
                    .parent(parent1)
                    .school("가온고등학교")
                    .grade(Grade.High1)
                    .seatNumber("A-01")
                    .registrationDate(LocalDate.now())
                    .emergencyContact("010-9999-8888")
                    .build());

            studentDetailRepository.save(StudentDetail.builder()
                    .member(student2)
                    .parent(parent1)
                    .school("가온여자고등학교")
                    .grade(Grade.High2)
                    .seatNumber("B-05")
                    .registrationDate(LocalDate.now())
                    .emergencyContact("010-9999-8888")
                    .build());

            // -----------------------------------
            // 6) 스케줄 + 외출 생성
            // -----------------------------------
            for (DayOfWeek day : DayOfWeek.values()) {

                // -----------------------------
                // 🔹 홍길동 스케줄
                // -----------------------------
                Schedule s1 = scheduleRepository.save(
                        Schedule.builder()
                                .student(student1)
                                .day(day)
                                .attendTime(LocalTime.of(14, 0))
                                .leaveTime(LocalTime.of(21, 0))
                                .memo("자동 생성 스케줄")
                                .status(ScheduleStatus.APPROVED)
                                .build()
                );

                // 🔹 홍길동 외출 (월 1개, 화 2개)
                if (day == DayOfWeek.MONDAY) {
                    outingRepository.save(Outing.builder()
                            .schedule(s1)
                            .title("치과 진료")
                            .startTime(LocalTime.of(17, 30))
                            .endTime(LocalTime.of(18, 10))
                            .build());
                }
                if (day == DayOfWeek.TUESDAY) {
                    outingRepository.save(Outing.builder()
                            .schedule(s1)
                            .title("약국 방문")
                            .startTime(LocalTime.of(18, 0))
                            .endTime(LocalTime.of(18, 20))
                            .build());

                    outingRepository.save(Outing.builder()
                            .schedule(s1)
                            .title("편의점")
                            .startTime(LocalTime.of(19, 30))
                            .endTime(LocalTime.of(19, 45))
                            .build());
                }

                // -----------------------------
                // 🔹 이영희 스케줄
                // -----------------------------
                Schedule s2 = scheduleRepository.save(
                        Schedule.builder()
                                .student(student2)
                                .day(day)
                                .attendTime(LocalTime.of(16, 0))
                                .leaveTime(LocalTime.of(20, 30))
                                .memo("자동 생성 스케줄")
                                .status(ScheduleStatus.APPROVED)
                                .build()
                );

                // 🔹 이영희 외출
                if (day == DayOfWeek.WEDNESDAY) {
                    outingRepository.save(Outing.builder()
                            .schedule(s2)
                            .title("학원 쉬는시간 외출")
                            .startTime(LocalTime.of(18, 10))
                            .endTime(LocalTime.of(18, 30))
                            .build());
                }
                if (day == DayOfWeek.FRIDAY) {
                    outingRepository.save(Outing.builder()
                            .schedule(s2)
                            .title("스터디카페 이동")
                            .startTime(LocalTime.of(17, 40))
                            .endTime(LocalTime.of(18, 10))
                            .build());

                    outingRepository.save(Outing.builder()
                            .schedule(s2)
                            .title("친구 만남")
                            .startTime(LocalTime.of(19, 20))
                            .endTime(LocalTime.of(19, 50))
                            .build());
                }
            }

            System.out.println("🔥 원장/관리자/학부모/학생 + 스케줄 + 외출 데이터 생성 완료!");
        };
    }
}
