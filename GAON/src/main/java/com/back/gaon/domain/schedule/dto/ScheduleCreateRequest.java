package com.back.gaon.domain.schedule.dto;

import com.back.gaon.domain.outing.dto.OutingCreateRequest;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequest {
    private DayOfWeek day;
    private LocalTime attendTime;
    private LocalTime leaveTime;

    private String memo;

    // 외출 목록
    private List<OutingCreateRequest> outings;
}
