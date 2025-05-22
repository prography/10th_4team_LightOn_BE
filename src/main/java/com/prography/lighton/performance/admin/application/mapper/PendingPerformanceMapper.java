package com.prography.lighton.performance.admin.application.mapper;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO.PerformanceArtistDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO.PerformanceApplicationDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import java.util.List;

public class PendingPerformanceMapper {

    public static PerformanceApplicationDTO toPendingPerformanceDTO(Performance performance) {
        return PerformanceApplicationDTO.of(
                performance.getId(),
                performance.getInfo().getPosterUrl(),
                performance.getInfo().getTitle(),
                toGenres(performance.getGenres()),
                performance.getSchedule(),
                performance.getLocation().getRegion().getSubRegion().getCode(),
                performance.getApproveStatus()
        );
    }

    public static GetPerformanceApplicationDetailResponseDTO toPendingPerformanceDetailResponseDTO(
            Performance performance) {
        List<PerformanceArtistDTO> artistDTOs = performance.getArtists().stream()
                .map(PerformanceArtist::getArtist)
                .map(artist -> PerformanceArtistDTO.of(
                        artist.getId(),
                        artist.getStageName(),
                        artist.getDescription()))
                .toList();

        return GetPerformanceApplicationDetailResponseDTO.of(
                performance.getId(),
                performance.getInfo(),
                artistDTOs,
                performance.getSchedule(),
                performance.getLocation().getRegion().getSubRegion().getCode(), // 프록시 객체를 직렬화 이슈로 인해서 fetch join 불가능
                performance.getType(),
                performance.getSeats(),
                performance.getProofUrl()
        );
    }

    private static List<String> toGenres(List<PerformanceGenre> performanceGenres) {
        return performanceGenres.stream()
                .map(PerformanceGenre::getGenre)
                .map(Genre::getName)
                .toList();
    }
}
