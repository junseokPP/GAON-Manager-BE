package com.back.gaon.domain.schedule.response;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record ItemsByDayResponse(
        Map<DayOfWeek, List<ItemResponse>> itemsByDay
) {}
