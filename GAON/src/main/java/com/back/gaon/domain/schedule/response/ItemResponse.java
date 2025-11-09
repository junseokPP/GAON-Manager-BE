package com.back.gaon.domain.schedule.response;

import java.time.LocalTime;

public record ItemResponse(
        Long id,
        String title,
        String description,
        LocalTime startTime,
        LocalTime endTime
) {}