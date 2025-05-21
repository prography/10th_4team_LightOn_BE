package com.prography.lighton.performance.admin.presentation.dto.response;

import com.prography.lighton.performance.users.domain.entity.enums.Seat;
import com.prography.lighton.performance.users.domain.entity.enums.Type;
import com.prography.lighton.performance.users.domain.entity.vo.Info;
import com.prography.lighton.performance.users.domain.entity.vo.Schedule;
import java.util.List;

public record GetPerformanceApplicationDetailResponseDTO(
        Long id,
        Info info,
        List<PerformanceArtistDTO> artists,
        Schedule schedule,
        Integer regionCode,
        Type type,
        List<Seat> seats,
        String proofUrl

) {
    public static GetPerformanceApplicationDetailResponseDTO of(
            Long id,
            Info info,
            List<PerformanceArtistDTO> artists,
            Schedule schedule,
            Integer regionCode,
            Type type,
            List<Seat> seats,
            String proofUrl
    ) {
        return new GetPerformanceApplicationDetailResponseDTO(id, info, artists, schedule, regionCode, type, seats,
                proofUrl);
    }

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
