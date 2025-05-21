package com.prography.lighton.performance.admin.presentation.dto.response;

import com.prography.lighton.performance.users.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.users.domain.entity.vo.Schedule;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetPerformanceApplicationListResponseDTO(Page<PerformanceApplicationDTO> performanceApplications) {

    public static GetPerformanceApplicationListResponseDTO of(Page<PerformanceApplicationDTO> performanceApplications) {
        return new GetPerformanceApplicationListResponseDTO(performanceApplications);
    }

    public record PerformanceApplicationDTO(
            Long id,
            String imageUrl,
            String title,
            List<String> genres,
            Schedule schedule,
            Integer region,
            ApproveStatus status
    ) {

        public static PerformanceApplicationDTO of(Long id, String imageUrl, String title,
                                                   List<String> genres, Schedule schedule, Integer region,
                                                   ApproveStatus status) {
            return new PerformanceApplicationDTO(id, imageUrl, title, genres, schedule, region, status);
        }
    }
}
