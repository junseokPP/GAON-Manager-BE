package com.back.gaon.domain.outing.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutingCreateRequest {
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
}
