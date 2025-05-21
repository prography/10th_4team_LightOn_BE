package com.prography.lighton.performance.admin.presentation.dto.response;

import com.prography.lighton.performance.users.domain.entity.enums.Seat;
import com.prography.lighton.performance.users.domain.entity.enums.Type;
import com.prography.lighton.performance.users.domain.entity.vo.Info;
import com.prography.lighton.performance.users.domain.entity.vo.Location;
import com.prography.lighton.performance.users.domain.entity.vo.Schedule;
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
