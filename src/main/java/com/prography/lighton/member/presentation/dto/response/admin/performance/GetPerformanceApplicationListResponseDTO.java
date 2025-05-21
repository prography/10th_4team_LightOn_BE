package com.prography.lighton.member.presentation.dto.response.admin.performance;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetPerformanceApplicationListResponseDTO(Page<PerformanceApplicationDTO> performanceApplications) {

    public record PerformanceApplicationDTO(
            Long id,
            String imageUrl,
            String title,
            List<String> genres,
            LocalDateTime date,
            Integer region,
            String status
    ) {

        public static PerformanceApplicationDTO of(Long id, String imageUrl, String title,
                                                   List<String> genres, LocalDateTime date, Integer region,
                                                   String status) {
            return new PerformanceApplicationDTO(id, imageUrl, title, genres, date, region, status);
        }
    }
}
