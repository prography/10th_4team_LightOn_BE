package com.prography.lighton.performance.admin.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetPerformanceRequestsListResponseDTO(Page<PerformanceRequestDTO> performanceApplications) {

    public static GetPerformanceRequestsListResponseDTO of(Page<PerformanceRequestDTO> performanceApplications) {
        return new GetPerformanceRequestsListResponseDTO(performanceApplications);
    }

    public record PerformanceRequestDTO(
            Long id,
            String imageUrl,
            String title,
            List<String> genres,
            Schedule schedule,
            Integer regionCode,
            String regionName,
            ApproveStatus status
    ) {

        public static PerformanceRequestDTO of(Long id, String imageUrl, String title,
                                                   List<String> genres, Schedule schedule, Integer regionCode,
                                                   String regionName,
                                                   ApproveStatus status) {
            return new PerformanceRequestDTO(id, imageUrl, title, genres, schedule, regionCode, regionName, status);
        }
    }
}
