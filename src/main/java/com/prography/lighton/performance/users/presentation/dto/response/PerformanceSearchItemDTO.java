package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceGenre;
import java.time.LocalDate;

public record PerformanceSearchItemDTO(
        String title,
        String thumbnailImageUrl,
        String genre,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PerformanceSearchItemDTO of(Performance p) {
        String firstGenre = p.getGenres().stream()
                .findFirst()
                .map(PerformanceGenre::getGenre)
                .map(Genre::getName)
                .orElse(null);

        return new PerformanceSearchItemDTO(
                p.getInfo().getTitle(),
                p.getInfo().getPosterUrl(),
                firstGenre,
                p.getSchedule().getStartDate(),
                p.getSchedule().getEndDate()
        );
    }
}

