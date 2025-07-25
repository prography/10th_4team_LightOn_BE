package com.prography.lighton.performance.admin.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetPerformanceApplicationsListResponseDTO(Page<PerformanceApplicationDTO> performanceApplications) {

    public static GetPerformanceApplicationsListResponseDTO of(Page<PerformanceApplicationDTO> performanceApplications) {
        return new GetPerformanceApplicationsListResponseDTO(performanceApplications);
    }

    public record PerformanceApplicationDTO(
            Long id,
            String imageUrl,
            String title,
            List<String> genres,
            Schedule schedule,
            Integer regionCode,
            String regionName,
            ApproveStatus status
    ) {

        public static PerformanceApplicationDTO of(Long id, String imageUrl, String title,
                                                   List<String> genres, Schedule schedule, Integer regionCode,
                                                   String regionName,
                                                   ApproveStatus status) {
            return new PerformanceApplicationDTO(id, imageUrl, title, genres, schedule, regionCode, regionName, status);
        }
    }
}
