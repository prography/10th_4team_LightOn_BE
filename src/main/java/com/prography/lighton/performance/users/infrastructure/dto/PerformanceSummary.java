package com.prography.lighton.performance.users.infrastructure.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PerformanceSummary(
        Long id,
        String title,
        String description,
        String posterUrl,
        LocalDate startDate,
        LocalTime startTime,
        Boolean isPaid,
        String regionName,
        List<String> genreNames
) {
}
