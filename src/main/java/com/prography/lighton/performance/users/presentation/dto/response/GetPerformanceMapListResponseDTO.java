package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.Performance;
import java.time.LocalDate;
import java.util.List;

public record GetPerformanceMapListResponseDTO(List<PerformanceMapDTO> performanceMapList) {

    public static GetPerformanceMapListResponseDTO from(List<Performance> performances) {
        List<PerformanceMapDTO> performanceMapList = performances.stream()
                .map(PerformanceMapDTO::from)
                .toList();
        return new GetPerformanceMapListResponseDTO(performanceMapList);
    }

    public record PerformanceMapDTO(
            Long id,
            String posterUrl,
            String title,
            Double latitude,
            Double longitude,
            LocalDate startDate,
            LocalDate endDate
    ) {

        public static PerformanceMapDTO from(Performance performance) {
            return new PerformanceMapDTO(
                    performance.getId(),
                    performance.getInfo().getPosterUrl(),
                    performance.getInfo().getTitle(),
                    performance.getLocation().getLatitude(),
                    performance.getLocation().getLongitude(),
                    performance.getSchedule().getStartDate(),
                    performance.getSchedule().getEndDate()
            );
        }
    }
}
