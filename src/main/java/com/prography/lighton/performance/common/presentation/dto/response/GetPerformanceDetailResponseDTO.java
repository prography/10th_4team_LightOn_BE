package com.prography.lighton.performance.common.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.util.List;
import lombok.Builder;

@Builder
public record GetPerformanceDetailResponseDTO(
        Long id,
        Info info,
        List<ArtistDTO> artists,
        List<String> genres,
        Schedule schedule,
        Integer regionCode,
        String regionName,
        Type type,
        List<Seat> seats,
        String proofUrl
) {
    public record ArtistDTO(Long id, String name, String description) {
        public static ArtistDTO of(Long id, String name, String description) {
            return new ArtistDTO(id, name, description);
        }
    }
}
