package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetPerformanceBrowseResponse(List<PerformanceSummaryDTO> performances) {

    public static GetPerformanceBrowseResponse of(List<PerformanceSummary> summaries) {
        List<PerformanceSummaryDTO> dtos = summaries.stream()
                .map(summary -> PerformanceSummaryDTO.of(
                        summary.id(),
                        summary.title(),
                        summary.description(),
                        summary.posterUrl(),
                        summary.genreNames(),
                        summary.startDate(),
                        summary.startTime(),
                        summary.isPaid(),
                        summary.regionName(),
                        summary.artistName()
                ))
                .toList();

        return new GetPerformanceBrowseResponse(dtos);
    }

    public record PerformanceSummaryDTO(
            Long id,
            String title,
            String description,
            String thumbnailImageUrl,
            List<String> genres,
            LocalDate startDate,
            LocalTime startTime,
            Boolean isPaid,
            String regionName,
            String artistName
    ) {
        public static PerformanceSummaryDTO of(
                Long id,
                String title,
                String description,
                String thumbnailImageUrl,
                List<String> genres,
                LocalDate startDate,
                LocalTime startTime,
                Boolean isPaid,
                String regionName,
                String artistName
        ) {
            return new PerformanceSummaryDTO(id, title, description, thumbnailImageUrl,
                    genres, startDate, startTime, isPaid, regionName, artistName);
        }
    }
}
