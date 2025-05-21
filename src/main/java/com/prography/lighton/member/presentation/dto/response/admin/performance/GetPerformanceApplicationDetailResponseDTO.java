package com.prography.lighton.member.presentation.dto.response.admin.performance;

import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.entity.vo.Info;
import com.prography.lighton.performance.domain.entity.vo.Location;
import com.prography.lighton.performance.domain.entity.vo.Schedule;
import java.util.List;

public record GetPerformanceApplicationDetailResponseDTO(
        Long id,
        Info info,
        PerformanceArtistDTO artist,
        Schedule schedule,
        Location location,
        Type type,
        List<Seat> seats,
        String proofUrl

) {
    public record PerformanceArtistDTO(
            Long id,
            String name,
            String description
    ) {
        public static PerformanceArtistDTO of(Long id, String name, String description) {
            return new PerformanceArtistDTO(id, name, description);
        }
    }
}
