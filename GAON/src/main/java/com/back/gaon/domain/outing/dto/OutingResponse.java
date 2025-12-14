package com.back.gaon.domain.outing.dto;

import com.back.gaon.domain.outing.entity.Outing;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutingResponse {
    private Long outingId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;

    public static OutingResponse from(Outing outing) {
        return OutingResponse.builder()
                .outingId(outing.getId())
                .title(outing.getTitle())
                .startTime(outing.getStartTime())
                .endTime(outing.getEndTime())
                .build();
    }
}
