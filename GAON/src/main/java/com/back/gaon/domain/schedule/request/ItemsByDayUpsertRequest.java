package com.back.gaon.domain.schedule.request;

import java.time.DayOfWeek;

public record ItemsByDayUpsertRequest(
        Map<DayOfWeek, List<ItemUpsertDto>> itemsByDay
) {}