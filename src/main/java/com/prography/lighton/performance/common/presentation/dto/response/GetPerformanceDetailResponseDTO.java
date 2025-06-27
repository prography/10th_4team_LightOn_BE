package com.prography.lighton.performance.common.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.util.List;

public record GetPerformanceDetailResponseDTO(
        Long id,
        Info info,
        List<PerformanceArtistDTO> artists,
        List<String> genres,
        Schedule schedule,
        Integer regionCode,
        String regionName,
        Type type,
        List<Seat> seats,
        Integer totalSeatsCount,
        Integer bookedSeatCount,
        String proofUrl

) {
    public static GetPerformanceDetailResponseDTO of(
            Long id,
            Info info,
            List<PerformanceArtistDTO> artists,
            List<String> genres,
            Schedule schedule,
            Integer regionCode,
            String regionName,
            Type type,
            List<Seat> seats,
            Integer totalSeatsCount,
            Integer bookedSeatCount,
            String proofUrl
    ) {
        return new GetPerformanceDetailResponseDTO(id, info, artists, genres, schedule, regionCode,
                regionName, type,
                seats,
                totalSeatsCount,
                bookedSeatCount,
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
