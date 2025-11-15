package com.back.gaon.domain.schedule.item.dto.request;

import java.time.LocalTime;

public record ItemUpsertDto(
        Long id,
        String title,
        String description,
        LocalTime startTime,
        LocalTime endTime
) {}