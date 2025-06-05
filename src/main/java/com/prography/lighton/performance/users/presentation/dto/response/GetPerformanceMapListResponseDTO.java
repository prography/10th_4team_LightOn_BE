package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import org.springframework.data.domain.Page;

public record GetPerformanceMapListResponseDTO(Page<PerformanceMapDTO> performanceMapList) {

    public static GetPerformanceMapListResponseDTO from(Page<Performance> performances) {
        return new GetPerformanceMapListResponseDTO(performances.map(PerformanceMapDTO::from));
    }

    public record PerformanceMapDTO(
            Long id,
            Info info,
            Location location,
            Schedule schedule
    ) {

        public static PerformanceMapDTO from(Performance performance) {
            return new PerformanceMapDTO(
                    performance.getId(),
                    performance.getInfo(),
                    performance.getLocation(),
                    performance.getSchedule()
            );
        }
    }
}
